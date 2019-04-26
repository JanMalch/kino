package io.github.janmalch.kino.security;

import java.util.Date;
import java.util.Objects;

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
  public Date getExpiration() {
    return expiration;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    JwtToken jwtToken = (JwtToken) o;
    return Objects.equals(tokenString, jwtToken.tokenString);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokenString);
  }
}
