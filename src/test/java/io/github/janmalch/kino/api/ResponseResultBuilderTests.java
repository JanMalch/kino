package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.problem.Problem;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ResponseResultBuilderTests {

  private final ResponseResultBuilder<Long> builder = new ResponseResultBuilder<>();

  @Test
  void success() {
    Response response = builder.success("test");
    assertEquals(200, response.getStatus());
    assertTrue(response.getEntity() instanceof SuccessMessage);
  }

  @Test
  void successPayloadArg() {
    Response response = builder.success(5L);
    assertEquals(200, response.getStatus());
    assertEquals(5L, response.getEntity());
  }

  @Test
  void failure() {
    Problem problem = Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR);
    Response response = builder.failure(problem);
    assertEquals(500, response.getStatus());
    assertTrue(response.getEntity().toString().contains("type"));
  }

  @Test
  void failureMinimalResponse() {
    Problem problem = Problem.builder().build();
    Response response = builder.failure(problem);
    assertTrue(response.getEntity().toString().contains("type"));
  }
}
