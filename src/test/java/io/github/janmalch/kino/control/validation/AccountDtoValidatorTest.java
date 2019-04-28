package io.github.janmalch.kino.control.validation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problem;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AccountDtoValidatorTest {

  @Test
  void validateValid() {
    AccountDto data = new AccountDto();
    data.setEmail("test@example.com");
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setRole(Role.GUEST);
    data.setBirthday(LocalDate.now());
    SignUpDtoValidator validator = new SignUpDtoValidator();
    Optional<Problem> result = validator.validate(data);
    assertTrue(result.isEmpty());
  }

  @Test
  void validatePropIsNull() {
    AccountDto data = new AccountDto();
    // email will be null
    data.setFirstName("Test");
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setRole(Role.GUEST);
    data.setBirthday(LocalDate.now());
    SignUpDtoValidator validator = new SignUpDtoValidator();
    Optional<Problem> result = validator.validate(data);
    assertTrue(result.isPresent());
    assertEquals("The required field 'email' is empty", result.get().getDetail());
  }

  @Test
  void validateDataNotNull() {
    var validator = new SignUpDtoValidator();
    try {
      validator.validate(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("SignUp data is null", e.getMessage());
    }
  }
}
