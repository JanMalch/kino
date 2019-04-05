package io.github.janmalch.kino.problem;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public interface Problem {

  URI DEFAULT_TYPE = URI.create("http://localhost:8080/kino");

  default URI getType() {
    return DEFAULT_TYPE;
  }

  @Nullable
  default String getTitle() {
    return null;
  }

  @Nullable
  default Response.StatusType getStatus() {
    return null;
  }

  @Nullable
  default String getDetail() {
    return null;
  }

  @Nullable
  default URI getInstance() {
    return null;
  }

  default Map<String, Object> getParameters() {
    return Collections.emptyMap();
  }

  static ProblemBuilder builder() {
    return new ProblemBuilder();
  }

  static Problem valueOf(Response.StatusType status) {
    return Problem.valueOf(status, null, null);
  }

  static Problem valueOf(Response.StatusType status, String detail) {
    return Problem.valueOf(status, detail, null);
  }

  static Problem valueOf(Response.StatusType status, URI instance) {
    return Problem.valueOf(status, null, instance);
  }

  static Problem valueOf(Response.StatusType status, String detail, URI instance) {
    return new ProblemBuilder()
        .status(status)
        .title(status.getReasonPhrase())
        .detail(detail)
        .instance(instance)
        .build();
  }

  static String toString(Problem problem) {
    return problem.getType().toString()
        + "{"
        + "title='"
        + problem.getTitle()
        + '\''
        + ", status="
        + problem.getStatus()
        + ", detail='"
        + problem.getDetail()
        + '\''
        + ", instance="
        + problem.getInstance()
        + ", parameters="
        + problem.getParameters()
        + '}';
  }
}
