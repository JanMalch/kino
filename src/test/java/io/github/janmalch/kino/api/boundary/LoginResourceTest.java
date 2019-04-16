package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.security.JwtTokenFactory;
import org.junit.jupiter.api.Test;

class LoginResourceTest {

  @Test
  void successForJwtResultBuilder() {
    var builder = new LoginResource.JwtResultBuilder();
    var token = new JwtTokenFactory().generateToken("test@example.com");
    var response = builder.success(token);
    assertEquals(200, response.getStatus());
    var entity = (TokenDto) response.getEntity();
    assertEquals(token.getTokenString(), entity.getToken());
  }

  private void createUser() {
    var resource = new UserResource();
    var dto = new SignUpDto();
    dto.setEmail("test@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday("1990-01-01");
    dto.setPassword("Start123");
    var signUpResponse = resource.signUp(dto);
    if (signUpResponse.getStatus() != 200) {
      fail("Failed to create user");
    }
  }

  @Test
  void login() {
    createUser();

    var resource = new LoginResource();
    var dto = new LoginDto();
    dto.setEmail("test@example.com");
    dto.setPassword("Start123");
    var response = resource.logIn(dto);
    assertEquals(200, response.getStatus());
    var tokenDto = (TokenDto) response.getEntity();
    assertNotNull(tokenDto.getToken());
  }

  @Test
  void loginFail() {
    var resource = new LoginResource();
    var dto = new LoginDto();
    dto.setEmail("test@example.com");
    dto.setPassword("Start123");
    var response = resource.logIn(dto);
    assertEquals(400, response.getStatus());
  }
}
