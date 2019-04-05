package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
