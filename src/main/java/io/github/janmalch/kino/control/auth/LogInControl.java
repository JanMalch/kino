package io.github.janmalch.kino.control.auth;

import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * When user login, this control checks if email exists in database and password matches with salt +
 * hash. If both are correct, a token will be generated as a return value.
 */
public class LogInControl extends ManagingControl<Token> {

  private Logger log = LoggerFactory.getLogger(LogInControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private final LoginDto data;
  private final PasswordManager pm = new PasswordManager();

  private final Problem invalidLogin =
      Problem.builder()
          .type("invalid-login")
          .title("Username or password is wrong")
          .status(Response.Status.BAD_REQUEST)
          .instance()
          .build();

  public LogInControl(LoginDto data) {
    this.data = data;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Token> result) {
    manage(repository);
    log.info("New login from " + data.getEmail());

    var query = new AccountByEmailSpec(data.getEmail(), repository);
    var referredUser = repository.queryFirst(query);
    if (referredUser.isEmpty()) {
      return result.failure(invalidLogin);
    }

    var userEntity = referredUser.get();

    if (!doPasswordsMatch(userEntity.getHashedPassword(), userEntity.getSalt())) {
      return result.failure(invalidLogin);
    }

    var factory = new JwtTokenFactory();
    var token = factory.generateToken(data.getEmail());
    return result.success(token);
  }

  boolean doPasswordsMatch(String hash, byte[] salt) {
    return pm.isSamePassword(data.getPassword(), hash, salt);
  }
}
