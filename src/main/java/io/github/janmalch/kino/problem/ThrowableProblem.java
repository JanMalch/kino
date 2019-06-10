package io.github.janmalch.kino.problem;

/** A runtime exception that always carries a problem with it. */
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
    this.problem = problem;
  }

  public Problem getProblem() {
    return problem;
  }
}
