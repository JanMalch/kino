package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.MovieDto;
import org.junit.jupiter.api.Test;

class UpdateMovieControlTest {

  @Test
  void executeFailing() {
    var control = new UpdateMovieControl(new MovieDto(), -1);
    var builder = new ResponseResultBuilder<Void>();
    var response = control.execute(builder);
    assertEquals(404, response.getStatus());
  }
}
