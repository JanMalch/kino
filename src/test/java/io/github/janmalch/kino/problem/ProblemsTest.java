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
      return;
    }
    fail("Should've thrown a ThrowableProblem");
  }
}
