package io.github.janmalch.kino.problem;

import javax.ws.rs.core.Response;

public class Problems {
  private Problems() {}

  public static <T> T requireNonNull(T data) {
    return Problems.requireNonNull(data, null);
  }

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
}
