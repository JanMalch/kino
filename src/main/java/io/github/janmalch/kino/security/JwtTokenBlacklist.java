package io.github.janmalch.kino.security;

import java.util.HashSet;
import java.util.Set;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * A Set of Token to be added to JwtTokenBlacklist when user logout from a session. This is
 * necessary, since there is no invalidation of JWT Token.
 */
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

  /**
   * When a valid Token is added to blacklist, it also checks for expired Tokens in Set wether those
   * invalid Tokens can be removed.
   *
   * @param token a valid token is to be added to blacklist
   */
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

  /** Check if Set of Token has expired Tokens which can be removed. */
  public void clearOutdatedBlacklistedToken() {
    blacklist.removeIf(Token::isExpired);
  }
}
