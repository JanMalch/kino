package io.github.janmalch.kino.util.either;

import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;

public class EitherResultBuilder<P> implements ResultBuilder<Either<P>, P> {

  @Override
  public Either<P> success(Success<P> payload) {
    return new Either<>(payload);
  }

  @Override
  public Either<P> failure(Problem problem) {
    return new Either<>(problem);
  }
}
