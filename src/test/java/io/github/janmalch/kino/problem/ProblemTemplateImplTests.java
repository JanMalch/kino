package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.Test;

public class ProblemTemplateImplTests {

  @Test
  public void generateProblemFromTemplate() {
    ProblemTemplateImpl impl =
        new ProblemTemplateImpl(
            "path", "Test title", "Custom details: %s, %d", Response.Status.INTERNAL_SERVER_ERROR);

    Problem problem =
        impl.generateProblem(Map.of("email", "janmalch@gmail.com"), "janmalch@gmail.com", 42);

    URI expectedType = UriBuilder.fromUri(Problem.DEFAULT_TYPE).path("path").build();
    assertEquals(expectedType, problem.getType());
    assertEquals("Test title", problem.getTitle());
    assertEquals("Custom details: janmalch@gmail.com, 42", problem.getDetail());
    assertEquals(500, problem.getStatus().getStatusCode());
    assertEquals("janmalch@gmail.com", problem.getParameters().get("email"));
  }
}
