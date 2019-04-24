package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class JwtTokenTest {

  @Test
  void getTokenString() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    assertNotNull(token.getTokenString());
  }

  @Test
  void getExpiration() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    String testSubject = "TestUser@mail.de";
    acc.setEmail(testSubject);
    Token token = factory.generateToken(acc.getEmail());
    assertTrue(token.getExpiration() instanceof Date);
  }

  @Test
  void getSubject() throws MalformedClaimException, InvalidJwtException {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    String testSubject = "TestUser@mail.de";
    acc.setEmail(testSubject);
    Token token = factory.generateToken(acc.getEmail());
    assertEquals(token.getSubject(), testSubject);
  }

  @Test
  void isNotExpired() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    assertFalse(token.isExpired());
  }

  @Test
  void isExpired() {
    JwtTokenFactory factory = new JwtTokenFactory();
    // create instantly invalid tokens
    factory.setTokenDuration(TimeUnit.SECONDS.toMillis(-10));
    Token token = factory.generateToken("TestUser@mail.de");
    assertTrue(token.isExpired());
  }
}
