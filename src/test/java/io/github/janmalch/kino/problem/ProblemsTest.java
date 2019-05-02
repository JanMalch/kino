package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ProblemsTest {

  @Test
  void requireNonNull() {
    try {
      int x = Problems.requireNonNull(5);
      assertEquals(5, x);
    } catch (ThrowableProblem e) {
      fail("Should not throw");
    }
  }

  @Test
  void requireNonNullFailing() {
    try {
      Problems.requireNonNull(null, "This should be provided");
      fail("Should've thrown an exception");
    } catch (ThrowableProblem e) {
      assertEquals("This should be provided", e.getMessage());

      var problem = e.getProblem();
      assertTrue(problem.getType().toString().contains("no-data-provided"));
      assertEquals("Failed to provide required data", problem.getTitle());
      assertEquals(Response.Status.BAD_REQUEST, problem.getStatus());
      assertEquals("This should be provided", problem.getDetail());
      assertNotNull(problem.getInstance());
      return;
    }
    fail("Should've thrown a ThrowableProblem");
  }

  @Test
  void requireEntity() {
    try {
      String x = Problems.requireEntity("example", 5, "no test example found");
      assertEquals("example", x);
    } catch (ThrowableProblem e) {
      fail("Should not throw");
    }
  }

  @Test
  void requireEntityFailing() {
    try {
      Problems.requireEntity(null, 5, "No test example found");
      fail("Should've thrown an exception");
    } catch (ThrowableProblem e) {
      assertEquals("No test example found", e.getMessage());

      var problem = e.getProblem();
      assertTrue(problem.getType().toString().contains("no-such-entity"));
      assertEquals("Failed to find entity for given ID", problem.getTitle());
      assertEquals(Response.Status.NOT_FOUND, problem.getStatus());
      assertEquals("No test example found", problem.getDetail());
      assertEquals(5L, problem.getParameters().get("id"));
      assertNotNull(problem.getInstance());
      return;
    }
    fail("Should've thrown a ThrowableProblem");
  }
}
