package io.github.janmalch.kino.control.account;

import static io.github.janmalch.kino.DomainAssertions.assertEntityMissing;
import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class GetAccountByIdControlTest {

  @Test
  void getAccountById() {
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

    Repository.add(existing);

    var control = new GetAccountByIdControl(existing.getId());
    var builder = new EitherResultBuilder<AccountInfoDto>();
    var response = control.execute(builder);
    var success = response.getSuccess();
    if (response.getStatus().getStatusCode() != 200) {
      fail("GetAccountByIdControl has failed " + response.getProblem().toString());
    }
    var data = success.getData();
    assertEquals(200, response.getStatus().getStatusCode());
    assertEquals("abc@example.com", data.getEmail());
    assertEquals(existing.getId(), data.getId());
  }

  @Test
  void executeFail() {
    var control = new GetAccountByIdControl(-1);
    var builder = new EitherResultBuilder<AccountInfoDto>();
    assertEntityMissing(() -> control.execute(builder));
  }
}
