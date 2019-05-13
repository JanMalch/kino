package io.github.janmalch.kino.control.validation;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.util.BeanUtils;
import java.util.*;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

public class BeanValidations<T> {

  private final String path;
  private final T data;

  public BeanValidations(@NotNull T data, String path) {
    this.data = Objects.requireNonNull(data);
    this.path = path;
  }

  public Optional<Problem> requireNotEmpty(String... requiredFields) {
    var fields = Set.of(requiredFields);
    return propStream()
        .filter(entry -> fields.isEmpty() || fields.contains(entry.getKey()))
        .filter(entry -> BeanUtils.isNullOrEmpty(entry.getValue()))
        .findFirst()
        .map(
            entry ->
                Problem.builder()
                    .type(path + "/empty-field")
                    .title("A required field is empty")
                    .status(Response.Status.BAD_REQUEST)
                    .detail(String.format("The required field '%s' is empty", entry.getKey()))
                    .parameter("missing", entry.getKey())
                    .instance()
                    .build());
  }

  Stream<Map.Entry<String, Object>> propStream() {
    return BeanUtils.getBeanProperties(data).entrySet().stream();
  }
}
