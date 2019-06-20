package io.github.janmalch.kino.control.validation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.auth.SignUpDto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class BeanValidationsTest {

  @Test
  void requireNotEmptyAll() {
    SignUpDto data = new SignUpDto();
    data.setEmail("test@example.com");
    data.setFirstName(""); // firstName is empty, which is not allowed
    data.setLastName("Account");
    data.setPassword("Start123");
    data.setBirthday(LocalDate.now());

    var validator = new BeanValidations<>(data, "test");
    var result = validator.requireNotEmpty(/* everything must be set*/ );
    assertTrue(result.isPresent());
    assertEquals("The required field 'firstName' is empty", result.get().getDetail());
  }

  @Test
  void requireNotEmptySelective() {
    SignUpDto data = new SignUpDto();
    data.setFirstName("Test");
    data.setEmail("test@example.com");
    // everything else will be null, but only those 2 will be checked

    var validator = new BeanValidations<>(data, "test");
    var result = validator.requireNotEmpty("firstName", "email");
    assertTrue(result.isEmpty());
  }
}
