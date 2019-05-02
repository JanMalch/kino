package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ThrowableProblemTest {

  @Test
  void getProblem() {
    var throwableProblem = new ThrowableProblem(Problem.valueOf(Response.Status.BAD_REQUEST));
    assertNotNull(throwableProblem.getProblem());
  }

  @Test
  void rethrow() {
    var baseException = new IllegalArgumentException("test");
    var throwableProblem =
        new ThrowableProblem(Problem.valueOf(Response.Status.BAD_REQUEST), baseException);
    assertNotNull(throwableProblem.getProblem());
    assertNotNull(throwableProblem.getCause());
  }
}
