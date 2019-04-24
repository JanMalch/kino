package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.UserRepository;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.security.TokenSecurityContext;
import io.github.janmalch.kino.success.Success;
import org.junit.jupiter.api.Test;

class LogoutResourceTest {

  @Test
  void successForJwtResultBuilder() {
    var builder = new LogoutResource.JwtResultBuilder();
    var token = new JwtTokenFactory().generateToken("test@example.com");
    var response = builder.success(token);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    var entity = (TokenDto) success.getData();
    assertEquals(token.getTokenString(), entity.getToken());
  }

  private Token createTokenAndAddInRepository() {
    JwtTokenFactory factory = new JwtTokenFactory();
    UserRepository repository = new UserRepository();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    repository.add(acc);
    return factory.generateToken(acc.getEmail());
  }

  @Test
  void logOut() {
    var token = createTokenAndAddInRepository();

    var context = new TokenSecurityContext(token);
    var resource = new LogoutResource();
    var response = resource.logOut(context);
    System.out.println(response.toString());
    assertEquals(200, response.getStatus());
  }
}
