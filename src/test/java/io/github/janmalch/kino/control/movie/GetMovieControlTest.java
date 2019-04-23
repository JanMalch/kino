package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.entity.Movie;
import org.junit.jupiter.api.Test;

class GetMovieControlTest {

  @Test
  void executeFailing() {
    var control = new GetMovieControl(-1);
    var builder = new ResponseResultBuilder<Movie>();
    var response = control.execute(builder);
    assertEquals(404, response.getStatus());
  }
}
