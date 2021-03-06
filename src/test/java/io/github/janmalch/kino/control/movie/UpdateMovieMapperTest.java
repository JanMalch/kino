package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.movie.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;

class UpdateMovieMapperTest {

  private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  void update() {
    var mapper = new UpdateMovieControl.UpdateMovieMapper();

    var movie = new Movie();
    var movieDto = new MovieDto();
    movieDto.setAgeRating(12);
    movieDto.setDuration(2.5F);

    var result = mapper.update(movieDto, movie);
    assertEquals(12, result.getAgeRating());
    assertEquals(2.5F, result.getDuration());
  }
}
