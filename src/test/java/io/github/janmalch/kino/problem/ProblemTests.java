package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

public class ProblemTests {

  @Test
  public void problemToString() {
    Problem problem = Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR);
    String expected =
        Problem.DEFAULT_TYPE.toString()
            + "{"
            + "title='Internal Server Error',"
            + " status=Internal Server Error,"
            + " detail='null',"
            + " instance=null,"
            + " parameters={}}";
    assertEquals(expected, Problem.toString(problem));
  }

  @Test
  void valueOf() {
    var problemWithDetail = Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR, "detail");
    assertEquals("detail", problemWithDetail.getDetail());
    var problemWithInstance =
        Problem.valueOf(Response.Status.INTERNAL_SERVER_ERROR, URI.create("test:instance"));
    assertEquals(URI.create("test:instance"), problemWithInstance.getInstance());
  }

  @Test
  void defaultResults() {
    var problem = new TestProblem();
    assertNotNull(problem.getType());
    assertNull(problem.getTitle());
    assertNull(problem.getStatus());
    assertNull(problem.getDetail());
    assertNull(problem.getInstance());
    assertEquals(0, problem.getParameters().size());
  }

  private static class TestProblem implements Problem {}
}
