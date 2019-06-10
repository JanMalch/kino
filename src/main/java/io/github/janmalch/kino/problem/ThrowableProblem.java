package io.github.janmalch.kino.problem;

import java.util.Objects;

/** A runtime exception that always carries a problem with it. The problem may not be null. */
public class ThrowableProblem extends RuntimeException {

  private final Problem problem;

  public ThrowableProblem(Problem problem) {
    this(problem, null, null);
  }

  public ThrowableProblem(Problem problem, String message) {
    this(problem, message, null);
  }

  public ThrowableProblem(Problem problem, Throwable cause) {
    this(problem, null, cause);
  }

  public ThrowableProblem(Problem problem, String message, Throwable cause) {
    super(message, cause);
    this.problem = Objects.requireNonNull(problem);
  }

  public Problem getProblem() {
    return problem;
  }
}
