package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.SignUpDto;
import org.junit.jupiter.api.Test;

public class SignUpControlTest {

  @Test
  public void validateSignUpDto() {
    SignUpDto data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday("1990-01-01");
    var control = new SignUpControl(data);
    var result = control.validateSignUpDto();
    assertTrue(result.isEmpty());
  }
}
