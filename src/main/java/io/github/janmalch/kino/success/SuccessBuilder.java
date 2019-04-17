package io.github.janmalch.kino.success;

import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class SuccessBuilder<T> {

  private String message;
  private Response.StatusType status;
  private T data;
  private URI instance;

  SuccessBuilder() {}

  /**
   * Sets the given String as the title
   *
   * @return this SuccessBuilder message
   */
  public SuccessBuilder<T> message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Sets the given StatusType as the status
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder<T> status(Response.StatusType status) {
    this.status = status;
    return this;
  }

  /**
   * Sets the given String as the title
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder<T> data(T data) {
    this.data = data;
    return this;
  }

  /**
   * Sets the given URI as the instance
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder<T> instance(URI instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Auto generates an instance based on the type and always adds a timestamp as queryParam
   *
   * @return this SuccessBuilder instance
   */
  public SuccessBuilder<T> instance() {
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
  public Success<T> build() {
    return new SuccessImpl<T>(message, status, data, instance);
  }
}
