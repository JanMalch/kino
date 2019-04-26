package io.github.janmalch.kino.control;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import javax.ws.rs.core.Response;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogOutControl implements Control<Token> {

  private Logger log = LoggerFactory.getLogger(LogInControl.class);
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();
  private Token token;

  private final Problem invalidLogout =
      Problem.builder()
          .type("invalid-logout")
          .title("Could not logout User")
          .detail("Could not logout User")
          .status(Response.Status.BAD_REQUEST)
          .instance()
          .build();

  public LogOutControl(Token token) {
    this.token = token;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Token> result) {
    try {
      log.info("Logout User " + token.getSubject());
    } catch (InvalidJwtException | MalformedClaimException e) {
      return result.failure(invalidLogout);
    }
    if (token.isExpired()) {
      return result.failure(invalidLogout);
    }

    blacklist.addToBlackList(token);

    var expiredToken = factory.invalidate();

    return result.success(expiredToken);
  }
}
