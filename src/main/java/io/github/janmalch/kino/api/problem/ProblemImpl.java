package io.github.janmalch.kino.api.problem;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.ws.rs.core.Response;

public class ProblemImpl implements Problem {

  private URI type;
  private String title;
  private Response.StatusType status;
  private String detail;
  private URI instance;
  private final Map<String, Object> parameters = new HashMap<>();

  ProblemImpl(URI type, String title, Response.StatusType status, String detail, URI instance) {
    this.type = type;
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.instance = instance;
  }

  ProblemImpl(
      URI type,
      String title,
      Response.StatusType status,
      String detail,
      URI instance,
      Map<String, Object> parameters) {
    this.type = type;
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.instance = instance;
    this.parameters.putAll(parameters);
  }

  ProblemImpl(URI type) {
    this(type, null, null, null, null);
  }

  ProblemImpl(URI type, String title) {
    this(type, title, null, null, null);
  }

  @Override
  public URI getType() {
    return type;
  }

  @Nullable
  @Override
  public String getTitle() {
    return title;
  }

  @Nullable
  @Override
  public Response.StatusType getStatus() {
    return status;
  }

  @Nullable
  @Override
  public String getDetail() {
    return detail;
  }

  @Nullable
  @Override
  public URI getInstance() {
    return instance;
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }
}
