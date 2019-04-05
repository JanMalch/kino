package io.github.janmalch.kino.control;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.control.validation.SignUpDtoValidator;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.UserRepository;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.util.Mapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUpControl implements Control<Object> {

  private Logger log = LoggerFactory.getLogger(SignUpControl.class);
  private final UserRepository repository = new UserRepository();

  private final SignUpDto data;

  public SignUpControl(SignUpDto data) {
    this.data = data;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Object> result) {
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
    var user = new SignUpMapper().mapToEntity(data);
    repository.add(user);

    // -- build success response --
    return result.success(new Object());
  }

  Optional<Problem> validateSignUpDto() {
    var validator = new SignUpDtoValidator();
    return validator.validate(data);
  }

  Optional<Problem> checkIfEmailExists() {
    Specification<Account> presentCheck = new UserByEmailSpec(data.getEmail());
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

  public static class SignUpMapper implements Mapper<Account, SignUpDto> {

    public static final SimpleDateFormat BIRTHDAY_FORMAT = new SimpleDateFormat("YYYY-MM-DD");

    @Override
    public Account mapToEntity(SignUpDto signUpDto) {
      Account account = new Account();
      account.setEmail(signUpDto.getEmail());
      account.setFirstName(signUpDto.getFirstName());
      account.setLastName(signUpDto.getLastName());
      try {
        account.setBirthday(BIRTHDAY_FORMAT.parse(signUpDto.getBirthday()));
      } catch (ParseException e) {
        // rethrow as unchecked as this should be handled by the validator
        throw new RuntimeException(e);
      }
      return account;
    }
  }
}