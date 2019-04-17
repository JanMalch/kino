package io.github.janmalch.kino.control;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;
import javax.annotation.Nullable;

public interface ResultBuilder<T, P> {
  T success(Success<P> payload);

  default T success(P data, @Nullable String message) {
    var success = Success.<P>builder().data(data).message(message).build();
    return this.success(success);
  }

  default T success(P data) {
    return this.success(data, null);
  }

  T failure(Problem problem);
}
