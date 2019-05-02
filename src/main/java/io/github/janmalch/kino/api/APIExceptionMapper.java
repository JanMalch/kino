package io.github.janmalch.kino.api;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.ThrowableProblem;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class APIExceptionMapper implements ExceptionMapper<Exception> {
  private Logger log = LoggerFactory.getLogger(APIExceptionMapper.class);

  @Override
  public Response toResponse(Exception e) {
    log.warn("Exception handled by APIExceptionMapper");
    var problem = Problem.builder(Response.Status.INTERNAL_SERVER_ERROR).instance().build();

    if (e instanceof ThrowableProblem) {
      problem = ((ThrowableProblem) e).getProblem();
      log.warn(Problem.toString(problem) + "\n", e.getCause());
    } else {
      log.warn(Problem.toString(problem) + "\n", e);
    }

    var builder = new ResponseResultBuilder<>();
    return builder.failure(problem);
  }
}
