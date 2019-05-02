package io.github.janmalch.kino.problem;

import java.util.Objects;
import javax.ws.rs.core.Response;

public class Problems {
  private Problems() {}

  /** @see Problems#requireNonNull(Object, String) */
  public static <T> T requireNonNull(T data) {
    return Problems.requireNonNull(data, null);
  }

  /**
   * Checks that the specified object reference is not {@code null} and throws a customized {@link
   * ThrowableProblem} if it is. This method is designed primarily for doing parameter validation in
   * controls. <b>Use it to indicate that required data was not provided at all.</b>
   *
   * <blockquote>
   *
   * <pre>
   * public SignUpControl(SignUpDto data) {
   *   this.data = Problems.requireNonNull(data, "No sign-up data provided");
   * }
   * </pre>
   *
   * </blockquote>
   *
   * @param data the object reference to check for nullity
   * @param detail the optional detail message for the problem in the event that a {@code
   *     ThrowableProblem} is thrown
   * @param <T> the type of the data
   * @return {@code data} if not {@code null}
   * @throws ThrowableProblem if {@code data} is {@code null}
   * @see Objects#requireNonNull(Object, String)
   */
  public static <T> T requireNonNull(T data, String detail) {
    if (data == null) {
      var problem =
          Problem.builder()
              .type("no-data-provided")
              .title("Failed to provide required data")
              .detail(detail)
              .status(Response.Status.BAD_REQUEST)
              .instance()
              .build();
      throw new ThrowableProblem(problem, detail);
    }
    return data;
  }

  /**
   * Checks that the specified object reference is not {@code null} and throws a customized {@link
   * ThrowableProblem} if it is. This method is designed primarily for checking if the given ID was
   * resolved to an entity. <b>Use it to indicate that no entity was found for the given ID.</b>
   *
   * <blockquote>
   *
   * <pre>
   * &#x00040;Override
   * public &lt;T> T execute(ResultBuilder&lt;T, PresentationDto> result) {
   *   var presentation = Problems.requireEntity(repository.find(id), id, "No presentation found");
   * }
   * </pre>
   *
   * </blockquote>
   *
   * @param data the entity reference to check for nullity
   * @param id the provided ID
   * @param detail the detail message for the problem in the event that a {@code ThrowableProblem}
   *     is thrown
   * @param <T> the type of the data
   * @return {@code data} if not {@code null}
   * @throws ThrowableProblem if {@code data} is {@code null}
   */
  public static <T> T requireEntity(T data, long id, String detail) {
    if (data == null) {
      var problem =
          Problem.builder()
              .type("no-such-entity")
              .title("Failed to find entity for given ID")
              .detail(detail)
              .status(Response.Status.NOT_FOUND)
              .instance()
              .parameter("id", id)
              .build();
      throw new ThrowableProblem(problem, detail);
    }
    return data;
  }
}
