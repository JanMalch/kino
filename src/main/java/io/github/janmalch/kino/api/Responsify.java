package io.github.janmalch.kino.api;

import io.github.janmalch.kino.problem.Problem;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

public class Responsify {

  public static Response fromProblem(Problem problem) {
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
    return Response.status(problem.getStatus()).entity(map).build();
  }
}
