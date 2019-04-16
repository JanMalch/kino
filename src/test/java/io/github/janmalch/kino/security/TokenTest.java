package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class TokenTest {

  @Test
  void getName() {
    var token = new TestToken();
    assertEquals(
        "Test",
        token.getName(),
        "Default implementation should return the subject as the principal name");
  }

  static class TestToken implements Token {

    @Override
    public String getTokenString() {
      return null;
    }

    @Override
    public String getSubject() throws InvalidJwtException, MalformedClaimException {
      return "Test";
    }

    @Override
    public boolean isExpired() {
      return false;
    }
  }
}
