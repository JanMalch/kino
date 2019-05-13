package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SignUpControlTest {

  @Test
  void validateSignUpDto() {
    var data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday(LocalDate.now());
    var control = new SignUpControl(data);
    var result = control.validateSignUpDto();
    assertTrue(result.isEmpty());
  }

  @Test
  void executeWithInvalidData() {
    var data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    // lastName missing
    data.setPassword("Start123");
    data.setBirthday(LocalDate.now());
    var control = new SignUpControl(data);
    var response = control.execute(new EitherResultBuilder<>());
    assertEquals(400, response.getStatus().getStatusCode());
    var problem = response.getProblem();
    assertEquals("A required field is empty", problem.getTitle());
    assertEquals("The required field 'lastName' is empty", problem.getDetail());
  }

  @Test
  void executeEmailExists() {
    var repository = RepositoryFactory.createRepository(Account.class);
    var existing = new Account();
    existing.setEmail("existing@example.com");
    repository.add(existing);

    var data = new SignUpDto();
    data.setEmail("existing@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday(LocalDate.now());

    var control = new SignUpControl(data);
    var response = control.execute(new EitherResultBuilder<>());
    assertEquals(400, response.getStatus().getStatusCode());
    var problem = response.getProblem();
    assertEquals(
        "An account with the email 'existing@example.com' already exists", problem.getDetail());
  }
}
