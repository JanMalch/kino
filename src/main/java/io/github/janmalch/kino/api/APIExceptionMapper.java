package io.github.janmalch.kino.api;

import io.github.janmalch.kino.problem.Problem;
import java.net.URI;
import java.util.Date;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class APIExceptionMapper implements ExceptionMapper<Exception> {
  private Logger log = LoggerFactory.getLogger(APIExceptionMapper.class);

  @Override
  public Response toResponse(Exception e) {
    URI instance =
        UriBuilder.fromUri(Problem.DEFAULT_TYPE).matrixParam("time", new Date().getTime()).build();

    Problem problem =
        Problem.valueOf(
            Response.Status.INTERNAL_SERVER_ERROR,
            "An unknown internal server error has occurred",
            instance);
    log.warn("Exception handled by APIExceptionMapper");
    log.warn(Problem.toString(problem), e);
    return new ResponseResultBuilder<>().failure(problem);
  }
}
