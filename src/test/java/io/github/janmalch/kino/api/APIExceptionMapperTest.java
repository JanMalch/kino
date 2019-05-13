package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.ThrowableProblem;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class APIExceptionMapperTest {

  @Test
  void toResponse() {
    var mapper = new APIExceptionMapper();
    var response = mapper.toResponse(new Exception("Test Exception"));
    assertEquals(500, response.getStatus());
  }

  @Test
  void toResponseWithThrowableProblem() {
    var mapper = new APIExceptionMapper();
    var problem = Problem.builder(Response.Status.NOT_FOUND).build();
    var response = mapper.toResponse(new ThrowableProblem(problem));
    assertEquals(404, response.getStatus());
  }
}
