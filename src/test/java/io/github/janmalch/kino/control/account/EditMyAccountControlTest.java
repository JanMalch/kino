package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.success.Success;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EditMyAccountControlTest {

  @Test
  void updateMyAccount() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("SomeExisting@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setRole(Role.CUSTOMER);
    existing.setSalt(salt);
    existing.setHashedPassword(hashedPassword);
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new SignUpDto();
    updateAccountDto.setEmail("updatedEmail@example.com");
    updateAccountDto.setFirstName("Test1");
    updateAccountDto.setLastName("Account1");
    updateAccountDto.setPassword("NewPassword");
    updateAccountDto.setBirthday(LocalDate.now());

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("SomeExisting@example.com");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Token>();
    var response = control.execute(builder);

    if (response.getStatus() != 200) {
      fail("updateMyAccount has failed " + response.getEntity());
    }
    // assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Token myAccountToken = (Token) success.getData();
    assertNotNull(myAccountToken);
    assertEquals(myAccountToken.getName(), updateAccountDto.getEmail());
  }

  @Test
  void updateMyAccountWithNoNewDataAvailable() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("SomeExisting1@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setRole(Role.CUSTOMER);
    existing.setSalt(salt);
    existing.setHashedPassword(hashedPassword);
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new SignUpDto();
    updateAccountDto.setEmail("SomeExisting1@example.com");

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("SomeExisting1@example.com");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Token>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Token myAccountToken = (Token) success.getData();
    assertNull(myAccountToken);
  }

  @Test
  void editMyAccountNotInRepository() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var existing = new Account();
    existing.setEmail("existing2@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new SignUpDto();
    updateAccountDto.setEmail("updatedEmail@example.com");
    updateAccountDto.setFirstName("Test1");
    updateAccountDto.setLastName("Account1");
    updateAccountDto.setBirthday(LocalDate.now());

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("TestUser2@mail.de");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Token>();
    var response = control.execute(builder);
    assertEquals(400, response.getStatus());
  }
}
