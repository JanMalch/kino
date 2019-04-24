package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import javax.security.auth.Subject;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class TokenTest {

  @Test
  void getName() throws MalformedClaimException, InvalidJwtException {
    var token = new TestToken();
    assertEquals(
        "Test",
        token.getSubject(),
        "Default implementation should return the subject as the principal name");
  }

  @Test
  void getNameFailing() {
    var token = new FailingToken();
    try {
      token.getName();
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof MalformedClaimException)) {
        fail("Unknown RuntimeException");
      }
    }
  }

  static class TestToken implements Token {

    @Override
    public String getTokenString() {
      return null;
    }

    @Override
    public String getSubject() {
      return "Test";
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public boolean implies(Subject subject) {
      return false;
    }

    @Override
    public boolean isExpired() {
      return false;
    }

    @Override
    public Date getExpiration() {
      return null;
    }
  }

  static class FailingToken implements Token {
    @Override
    public String getTokenString() {
      return null;
    }

    @Override
    public String getSubject() throws MalformedClaimException {
      throw new MalformedClaimException("Test claim");
    }

    @Override
    public boolean isExpired() {
      return false;
    }

    @Override
    public Date getExpiration() {
      return null;
    }
  }
}
