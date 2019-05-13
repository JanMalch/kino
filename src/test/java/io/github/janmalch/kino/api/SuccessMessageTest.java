package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SuccessMessageTest {

  @Test
  void setMessage() {
    var message = new SuccessMessage();
    message.setMessage("Something was successful");
    assertEquals("Something was successful", message.getMessage());
    assertNotNull(message.getType());
  }
}
