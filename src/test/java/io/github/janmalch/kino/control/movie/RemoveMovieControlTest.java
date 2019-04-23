package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import org.junit.jupiter.api.Test;

class RemoveMovieControlTest {

  @Test
  void executeFailing() {
    var control = new RemoveMovieControl(-1);
    var builder = new ResponseResultBuilder<Void>();
    var response = control.execute(builder);
    assertEquals(404, response.getStatus());
  }
}
