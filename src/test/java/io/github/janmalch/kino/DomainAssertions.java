package io.github.janmalch.kino;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.ThrowableProblem;
import io.github.janmalch.kino.util.functions.VoidConsumer;

public class DomainAssertions {

  private DomainAssertions() {}

  public static Problem assertEntityMissing(VoidConsumer fn) {
    var problem = catchProblem(fn);
    if (problem == null) {
      fail("Entity should be missing. No Problem was thrown via ThrowableProblem.");
    } else {
      assertEquals(404, problem.getStatus().getStatusCode());
    }
    return problem;
  }

  public static Problem catchProblem(VoidConsumer fn) {
    try {
      fn.execute();
      return null;
    } catch (ThrowableProblem e) {
      return e.getProblem();
    }
  }
}
