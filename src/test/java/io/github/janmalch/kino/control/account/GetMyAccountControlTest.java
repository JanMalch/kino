package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class GetMyAccountControlTest {

  @Test
  void GetMyAccountWithValidData() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var existing = new Account();
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");

    var control = new GetMyAccountControl(token.getName());
    var builder = new ResponseResultBuilder<SignUpDto>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus());
    var myAccount = response.getEntity();
    assertTrue(myAccount.toString().contains(existing.getEmail()));
  }

  @Test
  void getMyAccountNotInRepository() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var existing = new Account();
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("TestUser2@mail.de");

    var control = new GetMyAccountControl(token.getName());
    var builder = new ResponseResultBuilder<SignUpDto>();
    var response = control.execute(builder);
    assertEquals(400, response.getStatus());
    var myAccount = response.getEntity();
    assertFalse(myAccount.toString().contains(existing.getEmail()));
  }
}
