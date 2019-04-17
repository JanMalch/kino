package io.github.janmalch.kino.success;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class SuccessTest {

  @Test
  void defaultResults() {
    var success = new SuccessTest.TestSuccess();
    assertNull(success.getMessage());
    assertEquals(Response.Status.OK, success.getStatus());
    assertNull(success.getInstance());
    assertNull(success.getData());
  }

  @Test
  void valueOf() {
    var dataString = "Added a movie";
    var success = Success.valueOf(dataString);
    assertEquals(dataString, success.getData());
  }

  private static class TestSuccess implements Success {}
}
