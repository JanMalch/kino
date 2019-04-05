package io.github.janmalch.kino.control;

import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.UserRepository;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInControl implements Control<Token> {

  private Logger log = LoggerFactory.getLogger(LogInControl.class);
  private final UserRepository repository = new UserRepository();
  private final LoginDto data;

  private final Problem invalidLogin =
      Problem.builder()
          .type("invalid-login")
          .title("Username or password is wrong")
          .detail("Username or password is wrong")
          .status(Response.Status.BAD_REQUEST)
          .instance()
          .build();

  public LogInControl(LoginDto data) {
    this.data = data;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Token> result) {
    var query = new UserByEmailSpec(data.getEmail());
    var referredUser = repository.queryFirst(query);
    if (referredUser.isEmpty()) {
      return result.failure(invalidLogin);
    }

    var userEntity = referredUser.get();
    // TODO: change when hashing & salting is implemented
    if (!doPasswordsMatch(
        data.getPassword(), userEntity.getPassword(), /*userEntity.getSalt()*/ null)) {
      return result.failure(invalidLogin);
    }

    var factory = new JwtTokenFactory();
    var token = factory.generateToken(data.getEmail());
    return result.success(token);
  }

  // TODO: change when hashing & salting is implemented
  boolean doPasswordsMatch(String clearPassword, String hash, String salt) {
    return clearPassword.equals(hash);
  }
}
