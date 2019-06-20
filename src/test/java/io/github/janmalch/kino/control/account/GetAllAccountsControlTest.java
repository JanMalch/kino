package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.account.AccountInfoDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetAllAccountsControlTest {

  @Test
  void getAllAccounts() {
    Repository<Account> Repository = RepositoryFactory.createRepository(Account.class);
    PasswordManager pm = new PasswordManager();
    var salt = pm.generateSalt();
    var existing = new Account();
    var hashedPassword = pm.hashPassword("Password", salt);
    existing.setEmail("abc@example.com");
    existing.setFirstName("Test1");
    existing.setLastName("Account1");
    existing.setBirthday(LocalDate.now());
    existing.setSalt(salt);
    existing.setRole(Role.CUSTOMER);
    existing.setHashedPassword(hashedPassword);

    var existing2 = new Account();
    existing2.setEmail("abcd@example.com");
    existing2.setFirstName("Test1");
    existing2.setLastName("Account1");
    existing2.setBirthday(LocalDate.now());
    existing2.setSalt(salt);
    existing2.setRole(Role.CUSTOMER);
    existing2.setHashedPassword(hashedPassword);

    Repository.add(existing);
    Repository.add(existing2);

    var control = new GetAllAccountsControl();
    var builder = new EitherResultBuilder<List<AccountInfoDto>>();
    var response = control.execute(builder);
    if (response.getStatus().getStatusCode() != 200) {
      fail("GetAllAccountsControl has failed " + response.getProblem().toString());
    }
    assertEquals(200, response.getStatus().getStatusCode());
    var data = response.getSuccess();
    assertTrue(data.size() > 0);
  }

  @Test
  void executeFailing() throws IOException, ClassNotFoundException {
    EntityWiper ew = new EntityWiper();
    ew.deleteAll();
    var control = new GetAllAccountsControl();
    var builder = new EitherResultBuilder<List<AccountInfoDto>>();
    var response = control.execute(builder);
    var data = response.getSuccess();
    assertEquals(0, data.size());
  }
}
