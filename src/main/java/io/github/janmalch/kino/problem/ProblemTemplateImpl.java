package io.github.janmalch.kino.problem;

import java.net.URI;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public class ProblemTemplateImpl implements ProblemTemplate {

  private final String title;
  private final String detailTemplate;
  private final Response.StatusType status;
  private final URI type;

  public ProblemTemplateImpl(
      String path, String title, String detailTemplate, Response.StatusType status) {
    this.type = UriBuilder.fromUri(Problem.DEFAULT_TYPE).path(path).build();
    this.title = title;
    this.detailTemplate = detailTemplate;
    this.status = status;
  }

  @Override
  public Problem generateProblem(Map<String, Object> fields, Object... detailParams) {
    URI instance = UriBuilder.fromUri(type).queryParam("time", System.currentTimeMillis()).build();

    ProblemBuilder builder =
        Problem.builder()
            .type(type)
            .title(title)
            .status(status)
            .detail(String.format(detailTemplate, detailParams))
            .instance(instance);

    fields.forEach(builder::parameter);

    return builder.build();
  }
}
