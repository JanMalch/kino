package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.UserRepository;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class SignUpControlTest {

  @Test
  void validateSignUpDto() {
    var data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday("1990-01-01");
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
    data.setBirthday("1990-01-01");
    var control = new SignUpControl(data);
    var response = control.execute(new ResponseResultBuilder<>());
    assertEquals(400, response.getStatus());
    var problem = (HashMap<String, Object>) response.getEntity(); // TODO: refactor when new mapper
    assertEquals("A required field is empty", problem.get("title"));
    assertEquals("The required field 'lastName' is empty", problem.get("detail"));
  }

  @Test
  void executeEmailExists() {
    var repository = new UserRepository();
    var existing = new Account();
    existing.setEmail("existing@example.com");
    repository.add(existing);

    var data = new SignUpDto();
    data.setEmail("existing@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday("1990-01-01");

    var control = new SignUpControl(data);
    var response = control.execute(new ResponseResultBuilder<>());
    assertEquals(400, response.getStatus());
    var problem = (HashMap<String, Object>) response.getEntity(); // TODO: refactor when new mapper
    assertEquals(
        "An account with the email 'existing@example.com' already exists", problem.get("detail"));
  }
}
