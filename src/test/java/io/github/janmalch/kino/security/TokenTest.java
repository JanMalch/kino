package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.jsonwebtoken.SignatureException;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.jupiter.api.Test;

class TokenTest {

  @Test
  void createToken() {
    Token tokenClass = new Token();
    Account TestUser = new Account();
    TestUser.setEmail("Email@user.de");
    TestUser.setRole("admin");
    String token = tokenClass.createToken(TestUser);
    assertNotNull(token);
  }

  @Test
  void getAccountCredential() throws MalformedClaimException, InvalidJwtException {
    String expectedRole = "NoRole";
    Token tokenClass = new Token();
    Account TestUser = new Account();
    TestUser.setEmail("Email@user.de");

    TestUser.setRole(expectedRole);
    String token = tokenClass.createToken(TestUser);
    String role = tokenClass.getAccountCredential(token);
    assertEquals(role, expectedRole);
  }

  @Test
  void validateCorrectToken() {
    Token tokenClass = new Token();
    Account TestUser = new Account();
    TestUser.setEmail("Email@user.de");
    TestUser.setRole("Anfänger");
    String token = tokenClass.createToken(TestUser);
    assertTrue(tokenClass.validateToken(token));
  }

  @Test
  void validateIncorrectToken() {
    Token tokenClass = new Token();
    Account TestUser = new Account();
    TestUser.setEmail("Email@user.de");
    TestUser.setRole("Anfänger");
    String token =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbWFpbEB1c2VyLmRlIiwicm9sZSI6IkFkbWlzc3NzbiIsImV4cCI6MTU1MTQ5ODEzNH0.r3kKffGzcEFxsGUYIbrEP0w2S7IgyMPmVoJDyM6l2js";

    assertThrows(
        SignatureException.class,
        () -> {
          tokenClass.validateToken(token);
        });
  }
}
