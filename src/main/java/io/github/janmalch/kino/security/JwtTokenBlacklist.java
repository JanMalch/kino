package io.github.janmalch.kino.security;

import java.util.*;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

@Startup
@Singleton
public class JwtTokenBlacklist {

  private Set<Token> blacklist = new HashSet<>();

  private static JwtTokenBlacklist instance;

  public static JwtTokenBlacklist getInstance() {

    if (instance == null) {
      instance = new JwtTokenBlacklist();
    }
    return instance;
  }

  public void addToBlackList(String tokenString) throws MalformedClaimException {
    clearOutdatedBlacklistedToken();
    var factory = new JwtTokenFactory();
    Token token;
    try {
      token = factory.parse(tokenString);
      blacklist.add(token);
    } catch (InvalidJwtException e) {
      // token could be invalid at this time
      // this can be expected
    }
  }

  public boolean hasToken(Token token) {
    for (Token value : blacklist) {
      if (value.getTokenString().equals(token.getTokenString())) {
        return true;
      }
    }
    return false;
  }

  public boolean isBlacklisted(Token token) {
    return this.hasToken(token);
  }

  public void clearOutdatedBlacklistedToken() {
    blacklist.removeIf(Token::isExpired);
  }
}
