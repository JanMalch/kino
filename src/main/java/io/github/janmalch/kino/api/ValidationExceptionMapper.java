package io.github.janmalch.kino.api;

import io.github.janmalch.kino.problem.Problem;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    var parameters =
        exception
            .getConstraintViolations()
            .stream()
            .collect(
                Collectors.toMap(
                    (ConstraintViolation<?> cv) -> getLast(cv.getPropertyPath()),
                    ConstraintViolation::getMessage));

    var problem =
        Problem.builder()
            .type("invalid-data")
            .title("Provided data is invalid")
            .instance()
            .status(Response.Status.BAD_REQUEST)
            .parameter("invalid_fields", parameters)
            .build();
    return new ResponseResultBuilder<>().failure(problem);
  }

  // https://www.denismigol.com/posts/487/java-null-safe-iterable-getfirst-getlast-element

  public static <T> T getLast(Iterable<T> iterable) {
    return getLastOrDefault(iterable, null);
  }

  public static <T> T getLastOrDefault(Iterable<T> iterable, T defaultValue) {
    if (iterable == null) {
      return defaultValue;
    }
    if (iterable instanceof Collection) {
      Collection<T> collection = (Collection<T>) iterable;
      if (collection.isEmpty()) {
        return defaultValue;
      } else if (collection instanceof List) {
        List<T> list = (List<T>) collection;
        return list.get(list.size() - 1);
      }
    }
    Iterator<T> iterator = iterable.iterator();
    if (iterator.hasNext()) {
      T value;
      do {
        value = iterator.next();
      } while (iterator.hasNext());
      return value;
    }
    return defaultValue;
  }
}
