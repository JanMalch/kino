package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import java.text.SimpleDateFormat;
import org.junit.jupiter.api.Test;

class UpdateMovieMapperTest {

  private final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Test
  void updateEntity() {
    var mapper = new UpdateMovieControl.UpdateMovieMapper();

    var movie = new Movie();
    var movieDto = new MovieDto();
    movieDto.setAgeRating(12);
    movieDto.setDuration(2.5F);
    movieDto.setStartDate("2019-01-01");
    movieDto.setEndDate("2019-01-02");

    var result = mapper.updateEntity(movieDto, movie);
    assertEquals(12, result.getAgeRating());
    assertEquals(2.5F, result.getDuration());
    assertEquals("2019-01-01", dayFormat.format(result.getStartDate()));
    assertEquals("2019-01-02", dayFormat.format(result.getEndDate()));
  }
}
