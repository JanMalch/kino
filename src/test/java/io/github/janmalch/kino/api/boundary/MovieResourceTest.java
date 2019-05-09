package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.api.model.MovieOverviewDto;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.PriceCategory;
import io.github.janmalch.kino.success.Success;
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
    var success = (Success) response.getEntity();
    assertNull(success.getData());
  }

  @Test
  void getMovie() {
    Long movieId = persistNewMovie();

    var resource = new MovieResource();
    var response = resource.getMovie(movieId);

    var success = (Success) response.getEntity();
    var expectedEntity = (Movie) success.getData();

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
    var success = (Success) resource.getMovie(movieId).getEntity();
    var fetched = (Movie) success.getData();
    assertEquals("Wonder Woman", fetched.getName());
    assertNotNull(fetched.getStartDate(), "Updating should not overwrite with null");
  }

  @Test
  void getCurrentMovies() {
    Long movieId = persistNewMovie();
    var resource = new MovieResource();
    var response = resource.getCurrentMovies();
    var result = (MovieOverviewDto) ((Success) response.getEntity()).getData();
    assertTrue(result.getMovies().containsKey(movieId));
    assertFalse(result.getWeeks().isEmpty());
  }

  private Long persistNewMovie() {
    var resource = new MovieResource();
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("2019-01-01");
    dto.setEndDate("2019-12-02");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategory(createPriceCategory());
    var response = resource.newMovie(dto);
    var success = (Success) response.getEntity();
    return (Long) success.getData();
  }

  private PriceCategory createPriceCategory() {
    PriceCategory priceCategory = new PriceCategory();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }
}
