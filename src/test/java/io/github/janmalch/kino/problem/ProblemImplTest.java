package io.github.janmalch.kino.problem;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class ProblemImplTest {

  @Test
  void toString1() {
    var problem = new ProblemImpl(Problem.DEFAULT_TYPE);
    assertTrue(problem.toString().contains("type"));
  }

  @Test
  void twoArgConstructor() {
    var problem = new ProblemImpl(Problem.DEFAULT_TYPE, "test title");
    assertEquals(Problem.DEFAULT_TYPE, problem.getType());
    assertEquals("test title", problem.getTitle());
  }

  @Test
  void fiveArgConstructor() {
    var problem =
        new ProblemImpl(
            Problem.DEFAULT_TYPE,
            "test title",
            Response.Status.OK,
            "test detail",
            URI.create("about:instance"));
    assertEquals(Problem.DEFAULT_TYPE, problem.getType());
    assertEquals("test title", problem.getTitle());
    assertEquals(Response.Status.OK, problem.getStatus());
    assertEquals("test detail", problem.getDetail());
    assertEquals(URI.create("about:instance"), problem.getInstance());
  }
}
