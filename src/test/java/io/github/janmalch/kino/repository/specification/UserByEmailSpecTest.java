package io.github.janmalch.kino.repository.specification;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserByEmailSpecTest {

  @Test
  void toQuery() {
    var spec = new UserByEmailSpec("test@example.com");
    assertNotNull(spec.toQuery());
  }
}
