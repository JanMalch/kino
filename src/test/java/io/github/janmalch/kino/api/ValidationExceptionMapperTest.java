package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

class ValidationExceptionMapperTest {

  @Test
  void toResponse() {
    var exception = new ConstraintViolationException("Test message", Set.of());
    var response = new ValidationExceptionMapper().toResponse(exception);
    assertEquals(400, response.getStatus());
  }

  @Test
  void getLast() {
    // SortedSet required
    var set = new TreeSet<>(List.of(1, 2, 3));
    var last = ValidationExceptionMapper.getLast(set);
    assertEquals(3, last);
  }

  @Test
  void getLastForLists() {
    var last = ValidationExceptionMapper.getLast(List.of(1, 2, 3));
    assertEquals(3, last);
  }

  @Test
  void getLastForEmptyNonLists() {
    var set = new TreeSet<>(List.of());
    var last = ValidationExceptionMapper.getLast(set);
    assertNull(last);
  }

  @Test
  void getLastOrDefault() {
    var last = ValidationExceptionMapper.getLastOrDefault(null, 0);
    assertEquals(0, last);

    last = ValidationExceptionMapper.getLastOrDefault(List.of(), 10);
    assertEquals(10, last);
  }
}
