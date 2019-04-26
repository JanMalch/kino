package io.github.janmalch.kino.security;

import java.util.HashSet;
import java.util.Set;
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

  @Deprecated
  public void addToBlackList(String tokenString) throws MalformedClaimException {
    clearOutdatedBlacklistedToken();
    var factory = new JwtTokenFactory();
    Token token;
    try {
      token = factory.parse(tokenString);
      this.addToBlackList(token);
    } catch (InvalidJwtException e) {
      // token could be invalid at this time
      // this can be expected
    }
  }

  public void addToBlackList(Token token) {
    clearOutdatedBlacklistedToken();
    blacklist.add(token);
  }

  public boolean hasToken(Token token) {
    return blacklist.contains(token);
  }

  public boolean isBlacklisted(Token token) {
    return this.hasToken(token);
  }

  public void clearOutdatedBlacklistedToken() {
    blacklist.removeIf(Token::isExpired);
  }
}
