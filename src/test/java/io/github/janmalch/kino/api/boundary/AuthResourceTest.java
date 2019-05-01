package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.security.TokenSecurityContext;
import io.github.janmalch.kino.success.Success;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AuthResourceTest {

  @Test
  void successForJwtResultBuilder() {
    var builder = new AuthResource.JwtResultBuilder();
    var token = new JwtTokenFactory().generateToken("test@1example.com");
    var response = builder.success(token);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    var entity = (TokenDto) success.getData();
    assertEquals(token.getTokenString(), entity.getToken());
  }

  private void createUser() {
    var resource = new AccountResource();
    var dto = new SignUpDto();
    dto.setEmail("test2@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday(LocalDate.now());
    dto.setPassword("Start123");

    var signUpResponse = resource.signUp(dto);
    if (signUpResponse.getStatus() != 200) {
      System.out.println(signUpResponse.toString());
      fail("Failed to create user. " + signUpResponse.getEntity());
    }
  }

  @Test
  void login() {
    createUser();

    var resource = new AuthResource();
    var dto = new LoginDto();
    dto.setEmail("test2@example.com");
    dto.setPassword("Start123");
    var response = resource.logIn(dto);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    var tokenDto = (TokenDto) success.getData();
    assertNotNull(tokenDto.getToken());
  }

  @Test
  void loginFail() {
    var resource = new AuthResource();
    var dto = new LoginDto();
    dto.setEmail("test4@example.com");
    dto.setPassword("Start123");
    var response = resource.logIn(dto);
    assertEquals(400, response.getStatus());
  }

  private Token createTokenAndAddInRepository() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    repository.add(acc);
    return factory.generateToken(acc.getEmail());
  }

  @Test
  void logOut() {
    var token = createTokenAndAddInRepository();

    var context = new TokenSecurityContext(token);
    var resource = new AuthResource();
    var response = resource.logOut(context);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    var tokenDto = (TokenDto) success.getData();
    assertNotNull(tokenDto.getToken());
  }
}
