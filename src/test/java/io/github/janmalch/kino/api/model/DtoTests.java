package io.github.janmalch.kino.api.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DtoTests {

  @Test
  void tokenDto() {
    var token = new TokenDto();
    token.setToken("123");
    assertEquals("123", token.getToken());
  }

  @Test
  void signUpDto() {
    var signUp = new AccountDto();
    signUp.setEmail("test@example.com");
    assertEquals("test@example.com", signUp.getEmail());
    assertTrue(signUp.toString().contains("test@example.com"));
  }

  @Test
  void loginDto() {
    var login = new LoginDto();
    login.setEmail("test@example.com");
    login.setPassword("test123");
    assertEquals("test@example.com", login.getEmail());
    assertEquals("test123", login.getPassword());
    assertTrue(login.toString().contains("test123"));
  }

  @Test
  void pingDto() {
    var ping = new PingDto();
    ping.setResponse("poong");
    assertEquals("poong", ping.getResponse());
  }
}
