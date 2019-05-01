package io.github.janmalch.kino.api;

import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.success.Success;
import javax.ws.rs.core.Response;

public class ResponseResultBuilder<P> implements ResultBuilder<Response, P> {

  @Override
  public Response success(Success<P> payload) {
    return Response.ok(payload).build();
  }

  @Override
  public Response failure(Problem problem) {
    var responseStatus =
        problem.getStatus() != null ? problem.getStatus() : Response.Status.INTERNAL_SERVER_ERROR;
    return Response.status(responseStatus).type("application/problem+json").entity(problem).build();
  }
}
