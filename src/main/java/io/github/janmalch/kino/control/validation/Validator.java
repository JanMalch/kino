package io.github.janmalch.kino.control.validation;

import io.github.janmalch.kino.problem.Problem;
import java.util.Optional;

public interface Validator<T> {

  Optional<Problem> validate(T data);
}
