package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
