package io.github.janmalch.kino.security;

import java.util.Date;

public class JwtToken implements Token {
  private final String tokenString;
  private final String subject;
  private final Date expiration;

  JwtToken(String tokenString, String subject, Date expiration) {
    this.tokenString = tokenString;
    this.subject = subject;
    this.expiration = expiration;
  }

  @Override
  public String getTokenString() {
    return tokenString;
  }

  @Override
  public String getSubject() {
    return subject;
  }

  @Override
  public boolean isExpired() {
    return expiration.getTime() < System.currentTimeMillis();
  }
}
