package io.github.janmalch.kino.repository.specification;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountByEmailSpecTest {

  @Test
  void toQuery() {
    var spec = new AccountByEmailSpec("test@example.com");
    assertNotNull(spec.toQuery());
  }
}
