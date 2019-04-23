package io.github.janmalch.kino.util.either;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

public final class Either<P> {

  private final Success<P> success;
  private final Problem problem;

  public Either(@NotNull Problem problem) {
    this.success = null;
    this.problem = Objects.requireNonNull(problem);
  }

  public Either(@NotNull Success<P> success) {
    this.success = Objects.requireNonNull(success);
    this.problem = null;
  }

  public boolean isSuccess() {
    return this.success != null;
  }

  public boolean isFailure() {
    return !this.isSuccess();
  }

  public Response.StatusType getStatus() {
    return this.isSuccess() ? this.success.getStatus() : this.problem.getStatus();
  }

  @Nullable
  public Success<P> getSuccess() {
    return success;
  }

  @Nullable
  public Problem getProblem() {
    return problem;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Either<?> either = (Either<?>) o;
    return Objects.equals(getSuccess(), either.getSuccess())
        && Objects.equals(getProblem(), either.getProblem());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSuccess(), getProblem());
  }

  @Override
  public String toString() {
    return "Either{"
        + "isSuccess()="
        + isSuccess()
        + ", success="
        + success
        + ", problem="
        + problem
        + '}';
  }
}
