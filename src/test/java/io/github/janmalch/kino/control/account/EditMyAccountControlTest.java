package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.AccountDto;
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
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setRole(Role.CUSTOMER);
    existing.setSalt(salt);
    existing.setHashedPassword(hashedPassword);
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new AccountDto();
    updateAccountDto.setEmail("updatedEmail@example.com");
    updateAccountDto.setFirstName("Test1");
    updateAccountDto.setLastName("Account1");
    updateAccountDto.setPassword("NewPassword");
    updateAccountDto.setBirthday(LocalDate.now());
    updateAccountDto.setRole(Role.ADMIN); // should not be changed

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Account>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Account myAccount = (Account) success.getData();
    assertEquals(myAccount.getEmail(), updateAccountDto.getEmail());
    assertEquals(myAccount.getRole(), Role.CUSTOMER);
    assertTrue(
        pm.isSamePassword("NewPassword", myAccount.getHashedPassword(), myAccount.getSalt()));
  }

  @Test
  void updateMyAccountWithnoNewDataAvailable() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setRole(Role.CUSTOMER);
    existing.setSalt(salt);
    existing.setHashedPassword(hashedPassword);
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new AccountDto();

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("existing@example.com");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Account>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    Account myAccount = (Account) success.getData();
    assertEquals(myAccount.getEmail(), existing.getEmail());
    assertEquals(myAccount.getFirstName(), existing.getFirstName());
  }

  @Test
  void editMyAccountNotInRepository() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var existing = new Account();
    existing.setEmail("existing@example.com");
    existing.setFirstName("Test");
    existing.setLastName("Account");
    existing.setBirthday(LocalDate.now());
    repository.add(existing);

    var updateAccountDto = new AccountDto();
    updateAccountDto.setEmail("updatedEmail@example.com");
    updateAccountDto.setFirstName("Test1");
    updateAccountDto.setLastName("Account1");
    updateAccountDto.setBirthday(LocalDate.now());

    JwtTokenFactory factory = new JwtTokenFactory();
    Token token = factory.generateToken("TestUser2@mail.de");

    var control = new EditMyAccountControl(token, updateAccountDto);
    var builder = new ResponseResultBuilder<Account>();
    var response = control.execute(builder);
    assertEquals(400, response.getStatus());
  }
}
