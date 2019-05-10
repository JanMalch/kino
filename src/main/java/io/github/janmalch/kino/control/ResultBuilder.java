package io.github.janmalch.kino.control;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.problem.Problem;

public interface ResultBuilder<T, P> {
  T success(P payload);

  /**
   * Constructs a success response with SuccessMessage payload with the given message
   *
   * @param message human-friendly message
   * @return success for type T with SuccessMessage payload
   * @throws ClassCastException if the ResultBuilder is not for type SuccessMessage
   * @see SuccessMessage
   */
  default T success(String message) throws ClassCastException {
    var payload = (P) new SuccessMessage(message);
    return this.success(payload);
  }

  T failure(Problem problem);
}
