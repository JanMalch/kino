package io.github.janmalch.kino.api;

import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

public class ResponseResultBuilder<P> implements ResultBuilder<Response, P> {

  @Override
  public Response success(Success<P> payload) {
    return Response.ok(payload).build();
  }

  @Override
  public Response failure(Problem problem) {
    // TODO: replace Map with Jackson Serializer
    Map<String, Object> map = new HashMap<>(5);
    map.put("type", problem.getType());
    if (problem.getTitle() != null) {
      map.put("title", problem.getTitle());
    }
    if (problem.getStatus() != null) {
      map.put("status", problem.getStatus().getStatusCode());
    }
    if (problem.getDetail() != null) {
      map.put("detail", problem.getDetail());
    }
    if (problem.getInstance() != null) {
      map.put("instance", problem.getInstance());
    }
    map.putAll(problem.getParameters());
    return Response.status(problem.getStatus())
        .type("application/problem+json")
        .entity(map)
        .build();
  }
}
