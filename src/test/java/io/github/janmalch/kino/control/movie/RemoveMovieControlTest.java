package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import org.junit.jupiter.api.Test;

class RemoveMovieControlTest {

  @Test
  void executeFailing() {
    var control = new RemoveMovieControl(-1);
    var builder = new ResponseResultBuilder<SuccessMessage>();
    var response = control.execute(builder);
    assertEquals(404, response.getStatus());
  }
}
