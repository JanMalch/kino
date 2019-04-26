package io.github.janmalch.kino.security;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Singleton;
import javax.ejb.Startup;

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
