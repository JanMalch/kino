package io.github.janmalch.kino.util.either;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.problem.Problem;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class EitherResultBuilderTest {

  @Test
  void success() {
    var builder = new EitherResultBuilder<Long>();
    var either = builder.success(1L);
    assertTrue(either.isSuccess());
    assertNotNull(either.getSuccess());
    assertEquals(1L, either.getSuccess().getData());
    assertEquals(200, either.getStatus().getStatusCode());

    assertFalse(either.isFailure());
    assertNull(either.getProblem());
  }

  @Test
  void failure() {
    var builder = new EitherResultBuilder<Long>();
    var either = builder.failure(Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR));
    assertTrue(either.isFailure());
    assertNotNull(either.getProblem());
    assertEquals(
        Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), either.getProblem().getTitle());
    assertEquals(500, either.getStatus().getStatusCode());

    assertFalse(either.isSuccess());
    assertNull(either.getSuccess());
  }
}
