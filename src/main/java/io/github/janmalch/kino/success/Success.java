package io.github.janmalch.kino.success;

import java.net.URI;
import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public interface Success<T> {

  URI DEFAULT_TYPE = URI.create("http://localhost:8080/kino");

  @Nullable
  default String getMessage() {
    return null;
  }

  default Response.StatusType getStatus() {
    return Response.Status.OK;
  }

  @Nullable
  default T getData() {
    return null;
  }

  @Nullable
  default URI getInstance() {
    return null;
  }

  static <T> Success<T> valueOf(T data) {
    return new SuccessBuilder<T>().data(data).build();
  }

  static <T> SuccessBuilder<T> builder() {
    return new SuccessBuilder<>();
  }

  static String toString(Success<?> success) {
    return "Success{"
        + "message="
        + success.getMessage()
        + ", status='"
        + success.getStatus()
        + '\''
        + ", data="
        + success.getData()
        + '\''
        + ", instance="
        + success.getInstance()
        + '}';
  }
}
