package io.github.janmalch.kino.success;

import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public interface Success<T> {
  @Nullable
  default String getMessage() {
    return null;
  }

  @Nullable
  default Response.StatusType getStatus() {
    return Response.Status.OK;
  }

  @Nullable
  default T getData() {
    return null;
  }
}
