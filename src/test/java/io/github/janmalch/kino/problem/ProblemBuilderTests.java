package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProblemBuilderTests {

  @Test
  @DisplayName("reserved and custom fields")
  public void allFields() {
    URI baseUri = Problem.DEFAULT_TYPE;
    Problem problem =
        Problem.builder()
            .type(baseUri)
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .title("Unknown error")
            .detail("An unknown error as occurred")
            .instance(UriBuilder.fromUri(baseUri).matrixParam("timestamp", "test").build())
            .parameter("user", "janmalch")
            .build();

    assertEquals(baseUri, problem.getType());
    assertEquals(Response.Status.INTERNAL_SERVER_ERROR, problem.getStatus());
    assertEquals("Unknown error", problem.getTitle());
    assertEquals("An unknown error as occurred", problem.getDetail());
    assertEquals(
        UriBuilder.fromUri(baseUri).matrixParam("timestamp", "test").build(),
        problem.getInstance());

    var parameters = problem.getParameters();
    assertEquals("janmalch", parameters.get("user"));
  }

  @Test
  public void autoInstance() {
    Problem problem = Problem.builder().type(Problem.DEFAULT_TYPE).instance().build();
    assertTrue(problem.getInstance().toString().contains("time"));
  }

  @Test
  public void typePathAppending() {
    Problem problem = Problem.builder().type("test").build();
    assertEquals(problem.getType().toString(), Problem.DEFAULT_TYPE.toString() + "/test");
  }

  @Test
  public void preventReserved() {
    try {
      Problem.builder().parameter("status", "illegal");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("'status' is a reserved property", e.getMessage());
    }
  }
}
