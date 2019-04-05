package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
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
  void parse() throws MalformedClaimException, InvalidJwtException {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Token token = factory.generateToken(acc.getEmail());
    Token newToken = factory.parse(token.getTokenString());
    assertNotEquals(token, newToken);
    assertTrue(newToken.isExpired());
  }
}
