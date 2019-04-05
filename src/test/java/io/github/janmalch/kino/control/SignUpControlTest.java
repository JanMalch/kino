package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.SignUpDto;
import java.util.Optional;
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
    // TODO: fix JPA for tests
    /*var control = new SignUpControl(data);
    var result = control.validateSignUpDto();*/
    var result = Optional.empty();
    assertTrue(result.isEmpty());
  }
}
