package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.control.myaccount.DeleteMyAccountControl;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DeleteMyAccountControlTest {

  @Test
  void DeleteMyAccountWithValidData() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    existing.setSalt(salt);
    existing.setRole(Role.CUSTOMER);
    existing.setHashedPassword(hashedPassword);

    repository.add(existing);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");

    Specification<Account> myName = new AccountByEmailSpec(token.getName(), repository);

    var isInRepo = repository.queryFirst(myName);
    assertFalse(isInRepo.isEmpty());

    var control = new DeleteMyAccountControl(token);
    var builder = new ResponseResultBuilder<Token>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus());

    Token expiredToken = (Token) response.getEntity();
    assertEquals("", expiredToken.getTokenString());
    assertEquals("", expiredToken.getName());
    assertTrue(expiredToken.getExpiration().getTime() < System.currentTimeMillis());
  }

  @Test
  void DeleteMyAccountWithInValidData() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing1 = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing1.setEmail("existing1@example.com");
    existing1.setFirstName("Test");
    existing1.setLastName("Account");
    existing1.setBirthday(LocalDate.now());
    existing1.setSalt(salt);
    existing1.setRole(Role.CUSTOMER);
    existing1.setHashedPassword(hashedPassword);

    repository.add(existing1);

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("nonExisting@example.com");

    Specification<Account> myTokenName = new AccountByEmailSpec(token.getName(), repository);
    Specification<Account> myExistingName =
        new AccountByEmailSpec(existing1.getEmail(), repository);
    var tokenQuery = repository.queryFirst(myTokenName);
    assertTrue(tokenQuery.isEmpty());

    var control = new DeleteMyAccountControl(token);
    var builder = new ResponseResultBuilder<Token>();
    var response = control.execute(builder);
    assertEquals(400, response.getStatus());

    var isNotInRepo = repository.queryFirst(myExistingName);
    assertFalse(isNotInRepo.isEmpty());
  }
}
