package io.github.janmalch.kino.control.auth;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogOutControl implements Control<Token> {

  private Logger log = LoggerFactory.getLogger(LogInControl.class);
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();
  private Token token;

  public LogOutControl(Token token) {
    this.token = token;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Token> result) {
    log.info("Logout User " + token.getName());

    blacklist.addToBlackList(token);

    var expiredToken = factory.invalidate();

    if (token.isExpired()) {
      log.warn("Logout User: Token expired");
    }
    return result.success(expiredToken);
  }
}
