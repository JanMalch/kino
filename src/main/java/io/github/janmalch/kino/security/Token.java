package io.github.janmalch.kino.security;

import java.util.concurrent.TimeUnit;

public class Token {

  private String token;
  private long tokenDuration = TimeUnit.HOURS.toMillis(1); // duration = 1h

  Token() {}

  String getToken() {
    return token;
  }

  void setToken(String token) {
    this.token = token;
  }

  public long getTokenDuration() {
    return tokenDuration;
  }

  public void setTokenDuration(long tokenDuration) {
    this.tokenDuration = tokenDuration;
  }
}
