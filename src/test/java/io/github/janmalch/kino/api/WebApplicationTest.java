package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WebApplicationTest {

  @Test
  void create() {
    var app = new WebApplication();
    assertNotNull(app);
  }
}
