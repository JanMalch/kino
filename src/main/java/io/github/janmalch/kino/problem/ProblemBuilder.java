package io.github.janmalch.kino.problem;

import java.net.URI;
import java.util.*;
import javax.ws.rs.core.Response;

public class ProblemBuilder {

  private static final Set<String> RESERVED =
      new HashSet<>(Arrays.asList("type", "title", "status", "detail", "instance", "cause"));

  private URI type = Problem.DEFAULT_TYPE;
  private String title;
  private Response.StatusType status;
  private String detail;
  private URI instance;
  private final Map<String, Object> parameters = new HashMap<>();

  ProblemBuilder() {}

  public ProblemBuilder type(URI type) {
    this.type = type;
    return this;
  }

  public ProblemBuilder title(String title) {
    this.title = title;
    return this;
  }

  public ProblemBuilder status(Response.StatusType status) {
    this.status = status;
    return this;
  }

  public ProblemBuilder detail(String detail) {
    this.detail = detail;
    return this;
  }

  public ProblemBuilder instance(URI instance) {
    this.instance = instance;
    return this;
  }

  public Problem build() {
    return new ProblemImpl(type, title, status, detail, instance, parameters);
  }

  public ProblemBuilder parameter(String key, Object value) {
    if (RESERVED.contains(key)) {
      throw new IllegalArgumentException("'" + key + "' is a reserved property");
    }
    this.parameters.put(key, value);
    return this;
  }
}
