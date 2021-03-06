package io.github.janmalch.kino.problem;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

/**
 * Interface to describe a problem, defined by <a href="https://tools.ietf.org/html/rfc7807">RFC
 * 7807</a>. It's used to have a common schema for API error responses.
 *
 * <p>See <a href="https://github.com/zalando/problem">github.com/zalando/problem</a> for a complete
 * library.
 */
public interface Problem {

  URI DEFAULT_TYPE = URI.create("http://localhost:8080/kino");

  /**
   * A URI which is unique for each type of error. The only field that may not be null
   *
   * <p><hr/>
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * "type" (string) - A URI reference [RFC3986] that identifies the problem type. This
   * specification encourages that, when dereferenced, it provide human-readable documentation for
   * the problem type.
   *
   * </blockquote>
   *
   * @return a URI identifying the error
   */
  default URI getType() {
    return DEFAULT_TYPE;
  }

  /**
   *
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * "title" (string) - A short, human-readable summary of the problem type. It SHOULD NOT change
   * from occurrence to occurrence of the problem, except for purposes of localization
   *
   * </blockquote>
   *
   * @return a human-readable summary of the problem
   */
  @Nullable
  default String getTitle() {
    return null;
  }

  /**
   * The HTTP status code for the problem. This may be useful when proxies change the status type
   * later on. <hr/>
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * "status" (number) - The HTTP status code generated by the origin server for this occurrence of
   * the problem.
   *
   * </blockquote>
   *
   * @return HTTP status code for the problem
   */
  @Nullable
  default Response.StatusType getStatus() {
    return null;
  }

  /**
   *
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * "detail" (string) - A human-readable explanation specific to this occurrence of the problem.
   *
   * </blockquote>
   *
   * @return a human-readable explanation specific to this occurrence of the problem
   */
  @Nullable
  default String getDetail() {
    return null;
  }

  /**
   *
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * "instance" (string) - A URI reference that identifies the specific occurrence of the problem.
   * It may or may not yield further information if dereferenced.
   *
   * </blockquote>
   *
   * @return URI reference that identifies the specific occurrence of the problem
   */
  @Nullable
  default URI getInstance() {
    return null;
  }

  /**
   * A map to provide additional parameters to extend the problem schema.
   *
   * <p><hr>
   *
   * <h3>RFC 7807</h3>
   *
   * <blockquote>
   *
   * Problem type definitions MAY extend the problem details object with additional members.
   *
   * </blockquote>
   *
   * @return a map with additional parameters
   */
  default Map<String, Object> getParameters() {
    return Collections.emptyMap();
  }

  /**
   * Factory method to create a new {@link ProblemBuilder}
   *
   * @return a new {@link ProblemBuilder} instance
   */
  static ProblemBuilder builder() {
    return new ProblemBuilder();
  }

  /**
   * Factory method to create a new {@link ProblemBuilder}, initialized with the given {@link
   * Response.StatusType}. The following fields are initialized:
   *
   * <ul>
   *   <li>{@link Problem#getStatus() status}
   *   <li>{@link Problem#getType()} () type} - status' name as kebab case
   *   <li>{@link Problem#getTitle()} () title} - status' reason phrase
   * </ul>
   *
   * @param status the status to initialize the {@link ProblemBuilder} with
   * @return an initialized {@link ProblemBuilder} instance
   */
  static ProblemBuilder builder(Response.StatusType status) {
    var type = status.toEnum().name().toLowerCase().replaceAll("_", "-");
    return Problem.builder().type(type).status(status).title(status.getReasonPhrase());
  }

  /**
   * Convenience method to create a problem with the given status
   *
   * @param status {@link Problem#getStatus() status} to generate a problem from
   * @return a problem, described by the given parameters
   * @see Problem#valueOf(Response.StatusType, URI) valueOf(status, instance)<br>
   * @see Problem#valueOf(Response.StatusType, String) valueOf(status, detail)<br>
   * @see Problem#valueOf(Response.StatusType, String, URI) valueOf(status, detail, instance)
   */
  static Problem valueOf(Response.StatusType status) {
    return Problem.valueOf(status, null, null);
  }

  /**
   * Convenience method to create a problem with the given status and detail
   *
   * @param status {@link Problem#getStatus() status} to generate a problem from
   * @param detail {@link Problem#getStatus() detail} for the problem
   * @return a problem, described by the given parameters
   * @see Problem#valueOf(Response.StatusType) valueOf(status)<br>
   * @see Problem#valueOf(Response.StatusType, URI) valueOf(status, instance)<br>
   * @see Problem#valueOf(Response.StatusType, String, URI) valueOf(status, detail, instance)
   */
  static Problem valueOf(Response.StatusType status, String detail) {
    return Problem.valueOf(status, detail, null);
  }

  /**
   * Convenience method to create a problem with the given status and instance
   *
   * @param status {@link Problem#getStatus() status} to generate a problem from
   * @param instance {@link Problem#getInstance() instance} for the problem
   * @return a problem, described by the given parameters
   * @see Problem#valueOf(Response.StatusType) valueOf(status)<br>
   * @see Problem#valueOf(Response.StatusType, String) valueOf(status, detail)<br>
   * @see Problem#valueOf(Response.StatusType, String, URI) valueOf(status, detail, instance)
   */
  static Problem valueOf(Response.StatusType status, URI instance) {
    return Problem.valueOf(status, null, instance);
  }

  /**
   * Convenience method to create a problem with the given status and instance
   *
   * @param status {@link Problem#getStatus() status} to generate a problem from
   * @param detail {@link Problem#getStatus() detail} for the problem
   * @param instance {@link Problem#getInstance() instance} for the problem
   * @return a problem, described by the given parameters
   * @see Problem#valueOf(Response.StatusType) valueOf(status)<br>
   * @see Problem#valueOf(Response.StatusType, URI) valueOf(status, instance)<br>
   * @see Problem#valueOf(Response.StatusType, String) valueOf(status, detail)
   */
  static Problem valueOf(Response.StatusType status, String detail, URI instance) {
    return new ProblemBuilder()
        .status(status)
        .title(status.getReasonPhrase())
        .detail(detail)
        .instance(instance)
        .build();
  }

  /**
   * Method to create a String from the given problem
   *
   * @param problem the problem to represent as a string
   * @return a string representing the problem
   */
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
