package io.github.janmalch.kino.security;

import java.util.*;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

@Startup
@Singleton
public class JwtTokenBlacklist {

  Map<String, Date> blacklist = new HashMap<>();

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
    Date date;
    try {
      token = factory.parse(tokenString);
      date = token.getExpiration();
      blacklist.put(token.getTokenString(), date);
    } catch (InvalidJwtException e) {
      // token could be invalid at this time
      // this can be expected
    }
  }

  public boolean isBlacklisted(Token token) {
    return blacklist.containsKey(token.getTokenString());
  }

  public void clearOutdatedBlacklistedToken() {
    blacklist.entrySet().removeIf(entry -> entry.getValue().getTime() < System.currentTimeMillis());
  }
}
