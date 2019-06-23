package io.github.janmalch.kino.problem;

import io.github.janmalch.kino.util.Contract;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/** Builder, based on the builder pattern, to create a {@link Problem}. */
public class ProblemBuilder {

  /** Set of reserved properties: "type", "title", "status", "detail", "instance", "cause" */
  private static final Set<String> RESERVED =
      Set.of("type", "title", "status", "detail", "instance", "cause");

  private URI type = Problem.DEFAULT_TYPE;
  private String title;
  private Response.StatusType status;
  private String detail;
  private URI instance;
  private final Map<String, Object> parameters = new HashMap<>();

  ProblemBuilder() {}

  /**
   * Sets the given URI as the type
   *
   * @return this ProblemBuilder instance
   * @throws IllegalArgumentException if type is null
   */
  public ProblemBuilder type(@NotNull URI type) {
    Contract.require(type != null, "type for Problem may never be null");
    this.type = type;
    return this;
  }

  /**
   * Appends the given path to the default Problem type
   *
   * @return this ProblemBuilder instance
   * @see Problem#DEFAULT_TYPE
   */
  public ProblemBuilder type(String path) {
    this.type = UriBuilder.fromUri(Problem.DEFAULT_TYPE).path(path).build();
    return this;
  }

  /**
   * Sets the given String as the title
   *
   * @return this ProblemBuilder instance
   */
  public ProblemBuilder title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Sets the given StatusType as the status
   *
   * @return this ProblemBuilder instance
   */
  public ProblemBuilder status(Response.StatusType status) {
    this.status = status;
    return this;
  }

  /**
   * Sets the given String as the detail message
   *
   * @return this ProblemBuilder instance
   */
  public ProblemBuilder detail(String detail) {
    this.detail = detail;
    return this;
  }

  /**
   * Sets the given URI as the instance
   *
   * @return this ProblemBuilder instance
   */
  public ProblemBuilder instance(URI instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Auto generates an instance based on the type and always adds a timestamp as queryParam
   *
   * @return this ProblemBuilder instance
   * @throws IllegalStateException if the type is null
   */
  public ProblemBuilder instance() {
    this.instance = UriBuilder.fromUri(type).queryParam("time", System.currentTimeMillis()).build();
    return this;
  }

  /**
   * Adds the given custom parameter with the given value
   *
   * @return this ProblemBuilder instance
   * @throws IllegalArgumentException if the given key is a reserved property
   * @see ProblemBuilder#RESERVED
   */
  public ProblemBuilder parameter(String key, Object value) {
    Contract.require(!RESERVED.contains(key), "'" + key + "' is a reserved property");
    this.parameters.put(key, value);
    return this;
  }

  /**
   * Builds a Problem from the previously set values
   *
   * @return a Problem based on this builder's values
   */
  public Problem build() {
    return new ProblemImpl(type, title, status, detail, instance, parameters);
  }
}
