package io.github.janmalch.kino.control;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.problem.Problem;

/**
 * Interfaces to produce carrier objects, that can represent success or failures. All methods
 * produce an object of type T.
 *
 * @param <T> the (super) type for the success and failure objects
 * @param <P> the type of the payload, that success objects will carry
 */
public interface ResultBuilder<T, P> {
  /**
   * Constructs an object which indicates a successful result
   *
   * @param payload result of a successful operation
   * @return a carrier object, with the given payload
   */
  T success(P payload);

  /**
   * Constructs a success response with SuccessMessage payload with the given message
   *
   * @param message human-friendly message
   * @return success for type T with SuccessMessage payload
   * @see SuccessMessage
   */
  default T success(String message) {
    var payload = (P) new SuccessMessage(message);
    return this.success(payload);
  }

  /**
   * Constructs an object which indicates a failure
   *
   * @param problem a {@link Problem} describing a failed operation
   * @return a carrier object, with the given problem
   */
  T failure(Problem problem);
}
