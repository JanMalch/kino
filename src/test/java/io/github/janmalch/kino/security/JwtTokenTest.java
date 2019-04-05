package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
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
  void isExpired() throws InterruptedException {
    JwtTokenFactory factory = new JwtTokenFactory();
    factory.setTokenDuration(TimeUnit.SECONDS.toMillis(1));
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    Thread.sleep(1000);
    assertTrue(token.isExpired());
  }
}
