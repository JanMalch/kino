package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.janmalch.kino.api.model.account.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.security.TokenSecurityContext;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class MyAccountResourceTest {
  @Test
  void getMyAccount() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var existing = new Account();
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");
    var context = new TokenSecurityContext(token);

    var resource = new MyAccountResource();
    var response = resource.getMyAccount(context);
    assertEquals(200, response.getStatus());
    var myAccount = (SignUpDto) response.getEntity();
    assertEquals(existing.getEmail(), myAccount.getEmail());
  }

  @Test
  void editMyAccount() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var existing = new Account();
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var dto = new SignUpDto();
    dto.setEmail("test@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday(LocalDate.now());

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");
    var context = new TokenSecurityContext(token);

    var resource = new MyAccountResource();
    var response = resource.editMyAccount(dto, context);
    assertEquals(200, response.getStatus());
    var tokenDto = response.getEntity();
    assertNotNull(tokenDto);
  }

  @Test
  void deleteMyAccount() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var existing = new Account();
    existing.setEmail("Test@User.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("Test@User.com");
    var context = new TokenSecurityContext(token);

    var resource = new MyAccountResource();
    var response = resource.deleteMyAccount(context);
    assertEquals(200, response.getStatus());
  }
}
