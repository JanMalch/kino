package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import java.util.concurrent.TimeUnit;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class JwtTokenFactoryTest {

  @Test
  void refresh() throws MalformedClaimException, InvalidJwtException {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    Token newToken = factory.parse(token.getTokenString());
    assertNotEquals(token, newToken);
    assertFalse(token.isExpired());
  }

  @Test
  void parseAndReturnSameToken() throws MalformedClaimException, InvalidJwtException {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    Token newToken = factory.parse(token.getTokenString());
    assertEquals(token.getSubject(), newToken.getSubject());
    assertEquals(token.getTokenString(), newToken.getTokenString());
    assertEquals(token.isExpired(), newToken.isExpired());
    assertFalse(newToken.isExpired());
  }

  @Test
  void parseAndReturnExpiredToken() {
    JwtTokenFactory factory = new JwtTokenFactory();
    factory.setTokenDuration(TimeUnit.MILLISECONDS.toMillis(1));
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    assertThrows(
        InvalidJwtException.class,
        () -> {
          factory.parse(token.getTokenString());
        });
  }
}
