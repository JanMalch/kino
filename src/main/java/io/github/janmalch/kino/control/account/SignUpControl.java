package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.auth.SignUpDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.validation.SignUpDtoValidator;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpControl extends ManagingControl<SuccessMessage> {

  private Logger log = LoggerFactory.getLogger(SignUpControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final SignUpDto data;

  public SignUpControl(SignUpDto data) {
    this.data = data;
  }

  @Override
  public <T> T compute(ResultBuilder<T, SuccessMessage> result) {
    manage(repository);

    // -- validate prerequisites --
    var invalidDataProblem = validateSignUpDto();
    if (invalidDataProblem.isPresent()) {
      // this shouldn't happen if the frontend is used -> log.warn
      log.warn("Invalid sign-up data: " + data);
      return result.failure(invalidDataProblem.get());
    }

    var emailExistsProblem = checkIfEmailExists();
    if (emailExistsProblem.isPresent()) {
      return result.failure(emailExistsProblem.get());
    }

    // -- apply business logic --
    var user = new SignUpMapper().map(data);
    repository.add(user);

    // -- build success response --
    return result.success("Account successfully created");
  }

  Optional<Problem> validateSignUpDto() {
    var validator = new SignUpDtoValidator();
    return validator.validate(data);
  }

  Optional<Problem> checkIfEmailExists() {
    Specification<Account> presentCheck = new AccountByEmailSpec(data.getEmail(), repository);
    Optional<Account> referredUser = repository.queryFirst(presentCheck);

    // if a value is present, it means that the user exists and a Problem will be created
    // if no user is present in the Optional, it will return Optional.empty()
    return referredUser.map(
        user ->
            Problem.builder()
                .type("sign-up/email-taken")
                .title("An account with this email already exists")
                .status(Response.Status.BAD_REQUEST)
                .detail(
                    String.format("An account with the email '%s' already exists", data.getEmail()))
                .instance()
                .parameter("email", data.getEmail())
                .build());
  }

  public static class SignUpMapper implements Mapper<SignUpDto, Account> {

    private final PasswordManager pm = new PasswordManager();
    private final ReflectionMapper<SignUpDto, Account> mapper =
        new ReflectionMapper<>(Account.class);

    @Override
    public Account map(SignUpDto source) {
      var account = mapper.map(source);
      account.setRole(Role.CUSTOMER);

      var salt = pm.generateSalt();
      var hashedPw = pm.hashPassword(source.getPassword(), salt);
      account.setSalt(salt);
      account.setHashedPassword(hashedPw);

      return account;
    }
  }
}
