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
  default T getInstance() {
    return null;
  }

  static SuccessBuilder builder() {
    return new SuccessBuilder();
  }
}
