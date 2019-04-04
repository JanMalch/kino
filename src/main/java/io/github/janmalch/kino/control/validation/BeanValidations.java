package io.github.janmalch.kino.control.validation;

import io.github.janmalch.kino.problem.Problem;
import java.beans.IntrospectionException;
import java.beans.Introspector;
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
        .filter(entry -> isNullOrEmpty(entry.getValue()))
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

  /**
   * Checks whether the given value is null or its <code>value.toString().trim()</code> result is
   * empty
   *
   * @param value the value to check
   * @return true, if the value is null or toString is empty
   * @see String#toString()
   */
  public static boolean isNullOrEmpty(Object value) {
    return value == null || value.toString().trim().isEmpty();
  }

  Stream<Map.Entry<String, Object>> propStream() {
    return this.beanProperties().entrySet().stream();
  }

  // https://stackoverflow.com/a/8524043
  Map<String, Object> beanProperties() {
    try {
      Map<String, Object> map = new HashMap<>();
      Arrays.stream(
              Introspector.getBeanInfo(data.getClass(), Object.class).getPropertyDescriptors())
          // filter out properties with setters only
          .filter(pd -> Objects.nonNull(pd.getReadMethod()))
          .forEach(
              pd -> { // invoke method to get value
                try {
                  Object value = pd.getReadMethod().invoke(data);
                  map.put(pd.getName(), value);
                } catch (Exception e) {
                  // add proper error handling here
                }
              });
      return map;
    } catch (IntrospectionException e) {
      // add proper error handling here
      return Collections.emptyMap();
    }
  }
}
