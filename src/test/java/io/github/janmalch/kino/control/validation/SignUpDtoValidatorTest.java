package io.github.janmalch.kino.control.validation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.problem.Problem;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class SignUpDtoValidatorTest {

  @Test
  void validateValid() {
    SignUpDto data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("User");
    data.setPassword("Start123");
    data.setBirthday("1990-01-01");
    SignUpDtoValidator validator = new SignUpDtoValidator();
    Optional<Problem> result = validator.validate(data);
    assertTrue(result.isEmpty());
  }

  @Test
  void validatePropIsNull() {
    SignUpDto data = new SignUpDto();
    // email will be null
    data.setFirstName("Test");
    data.setLastName("User");
    data.setPassword("Start123");
    data.setBirthday("1990-01-01");
    SignUpDtoValidator validator = new SignUpDtoValidator();
    Optional<Problem> result = validator.validate(data);
    assertTrue(result.isPresent());
    assertEquals("The required field 'email' is empty", result.get().getDetail());
  }

  @Test
  void validateDateIsInvalid() {
    SignUpDto data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("User");
    data.setPassword("Start123");
    data.setBirthday("1990:01-01");
    SignUpDtoValidator validator = new SignUpDtoValidator();
    Optional<Problem> result = validator.validate(data);
    assertTrue(result.isPresent());
    assertEquals("The given birthday '1990:01-01' has an invalid format", result.get().getDetail());
  }
}
