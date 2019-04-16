package io.github.janmalch.kino.success;

import java.net.URI;
import java.util.Set;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class SuccessBuilder {
  /** Set of reserved properties: "message", "status", "data", "instance" */
  private static final Set<String> RESERVED = Set.of("message", "status", "data", "instance");

  private String message;
  private Response.StatusType status;
  private String data;
  private URI instance;

  SuccessBuilder() {}

  /**
   * Sets the given String as the title
   *
   * @return this SuccessBuilder message
   */
  public SuccessBuilder message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Sets the given StatusType as the status
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder status(Response.StatusType status) {
    this.status = status;
    return this;
  }

  /**
   * Sets the given String as the title
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder data(String data) {
    this.data = data;
    return this;
  }

  /**
   * Sets the given URI as the instance
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder instance(URI instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Auto generates an instance based on the type and always adds a timestamp as queryParam
   *
   * @return this SuccessBuilder instance
   * @throws IllegalStateException if the type is null
   */
  public SuccessBuilder instance() {
    this.instance =
        UriBuilder.fromUri(Success.DEFAULT_TYPE)
            .queryParam("time", System.currentTimeMillis())
            .build();
    return this;
  }

  /**
   * Builds a Success from the previously set values
   *
   * @return a Success based on this builder's values
   */
  public Success build() {
    return new SuccessImpl(message, status, data, instance);
  }
}
