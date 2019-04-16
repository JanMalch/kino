package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.boundary.UserResource;
import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.api.model.SignUpDto;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class LogInControlTest {

  @Test
  void executeWithInvalidPassword() {
    createUser();

    var dto = new LoginDto();
    dto.setEmail("logIn@example.com");
    dto.setPassword("Hilfe123");
    var control = new LogInControl(dto);
    var response = control.execute(new ResponseResultBuilder<>());
    assertEquals(400, response.getStatus());
    var problem = (HashMap<String, Object>) response.getEntity(); // TODO: refactor when new mapper
    assertEquals("Username or password is wrong", problem.get("title"));
  }

  private void createUser() {
    var resource = new UserResource();
    var dto = new SignUpDto();
    dto.setEmail("logIn@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday("1990-01-01");
    dto.setPassword("Start123");
    var signUpResponse = resource.signUp(dto);
    if (signUpResponse.getStatus() != 200) {
      fail("Failed to create user");
    }
  }
}
