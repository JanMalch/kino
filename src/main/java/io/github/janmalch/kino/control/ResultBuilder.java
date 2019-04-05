package io.github.janmalch.kino.control;

import io.github.janmalch.kino.problem.Problem;

public interface ResultBuilder<T, P> {
  T success(P payload);

  T failure(Problem problem);
}
