package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.jsonwebtoken.SignatureException;
import java.util.concurrent.TimeUnit;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class TokenFactoryTest {

  @Test
  void createToken() {
    TokenFactory tokenFactoryClass = new TokenFactory();
    Account TestUser = new Account();
    TestUser.setEmail("Test1@user.de");
    Token token = tokenFactoryClass.createToken(TestUser);
    assertNotNull(token.getToken());
  }

  @Test
  void getAccountCredential() throws MalformedClaimException, InvalidJwtException {
    String expectedRole = "NoRole";
    TokenFactory tokenFactoryClass = new TokenFactory();
    Account TestUser = new Account();
    TestUser.setEmail("Test2@user.de");
    TestUser.setRole(expectedRole);
    Token token = tokenFactoryClass.createToken(TestUser);
    String role = tokenFactoryClass.getAccountRole(token);
    assertEquals(role, expectedRole);
  }

  @Test
  void validateCorrectToken() {
    TokenFactory tokenFactoryClass = new TokenFactory();
    Account TestUser = new Account();
    TestUser.setEmail("Test3@user.de");
    Token token = tokenFactoryClass.createToken(TestUser);
    assertTrue(tokenFactoryClass.validateToken(token));
  }

  @Test
  void validateIncorrectToken() {
    TokenFactory tokenFactoryClass = new TokenFactory();
    // random String
    Token token = new Token();
    token.setToken(
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbWFpbEB1c2VyLmRlIiwicm9sZSI6IkFkbWlzc3NzbiIsImV4cCI6MTU1MTQ5ODEzNH0.r3kKffGzcEFxsGUYIbrEP0w2S7IgyMPmVoJDyM6l2js");
    assertThrows(SignatureException.class, () -> tokenFactoryClass.validateToken(token));
  }

  @Test
  void validateExpiredToken() {
    TokenFactory tokenFactoryClass = new TokenFactory();
    // random String
    Token token = new Token();
    token.setTokenDuration(TimeUnit.SECONDS.toMillis(1));
    assertThrows(IllegalArgumentException.class, () -> tokenFactoryClass.validateToken(token));
  }
}
