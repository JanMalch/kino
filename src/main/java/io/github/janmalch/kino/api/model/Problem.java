package io.github.janmalch.kino.api.model;

import java.net.URI;
import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public interface Problem {

  URI DEFAULT_TYPE = URI.create("about:blank");

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
}
