package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.security.TokenSecurityContext;
import io.github.janmalch.kino.success.Success;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AccountResourceTest {

  @Test
  void signUp() {
    var resource = new AccountResource();
    var dto = new SignUpDto();
    dto.setEmail("signUp@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday(LocalDate.now());
    dto.setPassword("Start123");
    var response = resource.signUp(dto);
    assertEquals(200, response.getStatus());

    // check if user exists
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var spec = new UserByEmailSpec("signUp@example.com");
    var account = repository.queryFirst(spec);
    assertTrue(account.isPresent());
    assertNotEquals("Start123", account.get().getHashedPassword());
  }

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

    var resource = new AccountResource();
    var response = resource.getMyAccount(context);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Account myAccount = (Account) success.getData();
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

    var resource = new AccountResource();
    var response = resource.editMyAccount(dto, context);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Token myAccountToken = (Token) success.getData();
    assertEquals(dto.getEmail(), myAccountToken.getName());
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

    var resource = new AccountResource();
    var response = resource.deleteMyAccount(context);
    assertEquals(200, response.getStatus());
  }

  @Test
  void getAccountById() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var existing = new Account();
    existing.setEmail("Test1@User.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var resource = new AccountResource();
    var response = resource.getAccountById(existing.getId());
    var success = (Success) response.getEntity();
    var data = success.getData();
    assertEquals(200, response.getStatus());
    assertNotNull(data);
  }

  @Test
  void getAllAccounts() throws IOException, ClassNotFoundException {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    EntityWiper ew = new EntityWiper();
    ew.deleteAll();

    var existing = new Account();
    existing.setEmail("Test2@User.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);
    var existing2 = new Account();
    existing2.setEmail("Test3@User.com");
    existing2.setFirstName("Test");
    existing2.setLastName("Account");
    existing2.setBirthday(LocalDate.now());
    repository.add(existing2);

    var resource = new AccountResource();
    var response = resource.getAllAccounts();
    var success = (Success) response.getEntity();
    var data = success.getData();
    assertEquals(200, response.getStatus());
    assertNotNull(data);
  }
}