package io.github.janmalch.kino.api;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.ThrowableProblem;
import javax.ws.rs.ForbiddenException;
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
    Throwable logException = e;

    if (e instanceof ThrowableProblem) {
      problem = ((ThrowableProblem) e).getProblem();
      logException = e.getCause();
    } else if (e instanceof ForbiddenException) {
      problem = Problem.builder(Response.Status.FORBIDDEN).instance().build();
    }

    log.warn(Problem.toString(problem) + "\n", logException);

    var builder = new ResponseResultBuilder<>();
    return builder.failure(problem);
  }
}
