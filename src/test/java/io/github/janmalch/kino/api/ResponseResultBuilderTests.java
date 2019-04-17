package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ResponseResultBuilderTests {

  private final ResponseResultBuilder<String> builder = new ResponseResultBuilder<>();

  @Test
  void success() {
    var input = Success.valueOf("test");
    Response response = builder.success(input);
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    assertEquals("test", success.getData());
  }

  @Test
  void successTwoArgs() {
    Response response = builder.success("my data", "my message");
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    assertEquals("my data", success.getData());
    assertEquals("my message", success.getMessage());
  }

  @Test
  void successPayloadArg() {
    Response response = builder.success("test");
    assertEquals(200, response.getStatus());
    var success = (Success) response.getEntity();
    assertEquals("test", success.getData());
  }

  @Test
  void failure() {
    Problem problem = Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR);
    Response response = builder.failure(problem);
    assertEquals(500, response.getStatus());
    assertTrue(response.getEntity().toString().contains("type"));
  }
}
