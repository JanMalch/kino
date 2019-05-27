package io.github.janmalch.kino.control.account;

import static io.github.janmalch.kino.DomainAssertions.assertEntityMissing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EditAccountByIdTest {

  @Test
  void executeFail() {
    AccountInfoDto acc = new AccountInfoDto();
    acc.setId(-1);

    var control = new EditAccountById(acc);
    var builder = new EitherResultBuilder<SuccessMessage>();
    assertEntityMissing(() -> control.execute(builder));
  }

  @Test
  void editAccountById() throws IOException, ClassNotFoundException {
    EntityWiper ew = new EntityWiper();
    ew.deleteAll();
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

    AccountInfoDto dto = new AccountInfoDto();
    dto.setId(existing.getId());
    dto.setEmail("TestUser1@email.de");
    dto.setRole(Role.MODERATOR);
    dto.setPassword("NewPassword");

    var control = new EditAccountById(dto);
    var builder = new EitherResultBuilder<SuccessMessage>();
    var response = control.execute(builder);
    assertEquals(200, response.getStatus().getStatusCode());
    assertNotNull(response.getSuccess());
  }
}
