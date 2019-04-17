package io.github.janmalch.kino.success;

import java.net.URI;
import javax.ws.rs.core.Response;

public class SuccessImpl<T> implements Success<T> {
  private String message;
  private Response.StatusType status;
  private T data;
  private URI instance;

  SuccessImpl(String message, Response.StatusType status, T data, URI instance) {
    this.message = message;
    this.status = status;
    this.data = data;
    this.instance = instance;
  }

  @Override
  public Response.StatusType getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public URI getInstance() {
    return instance;
  }

  @Override
  public String toString() {
    return "SuccessImpl{"
        + "message="
        + message
        + ", status='"
        + status
        + '\''
        + ", data="
        + data
        + '\''
        + ", instance="
        + instance
        + '}';
  }
}
