package io.github.janmalch.kino.util.either;

import io.github.janmalch.kino.problem.Problem;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

/**
 * Simple wrapper class for a result semantic. It can either carry a payload from a {@link
 * Either#getSuccess() success} or a {@link Problem} from a {@link Either#getProblem() failure}.
 *
 * @param <P> the payload type from a success
 */
public final class Either<P> {

  private final P success;
  private final Problem problem;

  /**
   * Constructs a new Either object which represents a failure.
   *
   * @param problem the problem from a failed process. May not be null
   */
  public Either(@NotNull Problem problem) {
    this.success = null;
    this.problem = Objects.requireNonNull(problem);
  }

  /**
   * Constructs a new Either object which represents a success.
   *
   * @param success the payload from a successful process. May not be null
   */
  public Either(@NotNull P success) {
    this.success = Objects.requireNonNull(success);
    this.problem = null;
  }

  /**
   * Indicates whether this Either instance represents a success or not
   *
   * @return true, if this instance represents a success
   */
  public boolean isSuccess() {
    return this.success != null;
  }

  /**
   * Indicates whether this Either instance represents a failure or not
   *
   * @return true, if this instance represents a failure
   */
  public boolean isFailure() {
    return !this.isSuccess();
  }

  /**
   * Returns {@link Response.Status#OK} if {@link Either#isSuccess()} is true. If not the status
   * will be taken from the problem and thus might be null.
   *
   * @return the status determined by the underlying result
   */
  public Response.StatusType getStatus() {
    return this.isSuccess() ? Response.Status.OK : this.problem.getStatus();
  }

  /**
   * Returns the success payload. Returns null, if this instance represents a failure.
   *
   * @return the success payload, if present
   */
  @Nullable
  public P getSuccess() {
    return success;
  }

  /**
   * Returns the problem. Returns null, if this instance represents a success.
   *
   * @return the problem, if present
   */
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
