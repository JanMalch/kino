package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import org.junit.jupiter.api.Test;

class MovieResourceTest {

  @Test
  void newMovie() {
    Long movieId = createMovie();
    assertTrue(movieId > 0);
  }

  @Test
  void deleteMovie() {
    Long movieId = createMovie();

    var resource = new MovieResource();
    var response = resource.deleteMovie(movieId);
    assertNull(response.getEntity());
  }

  @Test
  void getMovie() {
    Long movieId = createMovie();

    var resource = new MovieResource();
    var response = resource.getMovie(movieId);

    var expectedEntity = (Movie) response.getEntity();

    assertEquals("Captain Marvel", expectedEntity.getName());
  }

  private Long createMovie() {
    var resource = new MovieResource();
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("2019-01-01");
    dto.setEndDate("2019-01-02");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategory("1");
    var response = resource.newMovie(dto);
    return (Long) response.getEntity();
  }
}
