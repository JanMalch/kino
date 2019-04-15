package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import org.junit.jupiter.api.Test;

class MovieResourceTest {

  @Test
  void newMovie() {
    Long movieId = persistNewMovie();
    assertTrue(movieId > 0);
  }

  @Test
  void deleteMovie() {
    Long movieId = persistNewMovie();

    var resource = new MovieResource();
    var response = resource.deleteMovie(movieId);
    assertNull(response.getEntity());
  }

  @Test
  void getMovie() {
    Long movieId = persistNewMovie();

    var resource = new MovieResource();
    var response = resource.getMovie(movieId);

    var expectedEntity = (Movie) response.getEntity();

    assertEquals("Captain Marvel", expectedEntity.getName());
  }

  @Test
  void updateMovie() {
    Long movieId = persistNewMovie();
    var resource = new MovieResource();

    var update = new MovieDto();
    update.setName("Wonder Woman");
    var response = resource.updateMovie(update, movieId);
    assertEquals(200, response.getStatus());

    // check if update has successfully been merged
    var fetched = (Movie) resource.getMovie(movieId).getEntity();
    assertEquals("Wonder Woman", fetched.getName());
  }

  private Long persistNewMovie() {
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
