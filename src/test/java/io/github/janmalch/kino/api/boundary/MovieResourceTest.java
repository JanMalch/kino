package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.movie.MovieDto;
import io.github.janmalch.kino.api.model.movie.MovieOverviewDto;
import io.github.janmalch.kino.api.model.movie.NewMovieDto;
import io.github.janmalch.kino.api.model.pricecategory.PriceCategoryDto;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieResourceTest {

  @BeforeEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  void getAllMovies() {
    persistNewMovie();
    var resource = new MovieResource();
    var response = resource.getAllMovies();
    var result = (List<MovieDto>) response.getEntity();
    assertFalse(result.isEmpty());
  }

  @Test
  void newMovie() {
    Long movieId = persistNewMovie();
    assertTrue(movieId > 0);
  }

  @Test
  void deleteMovie() {
    Long movieId = persistNewMovie(false);

    var resource = new MovieResource();
    var response = resource.deleteMovie(movieId);
    var entity = (SuccessMessage) response.getEntity();
    assertEquals("Movie has been removed", entity.getMessage());
    assertNotNull(entity.getType());
  }

  @Test
  void getMovie() {
    Long movieId = persistNewMovie();

    var resource = new MovieResource();
    var response = resource.getMovie(movieId);

    var expectedEntity = (MovieDto) response.getEntity();

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
    var fetched = (MovieDto) resource.getMovie(movieId).getEntity();
    assertEquals("Wonder Woman", fetched.getName());
  }

  @Test
  void getCurrentMovies() {
    Long movieId = persistNewMovie(true);
    var resource = new MovieResource();
    var response = resource.getCurrentMovies();
    var result = (MovieOverviewDto) response.getEntity();
    assertTrue(result.getMovies().containsKey(movieId));
    assertFalse(result.getWeeks().isEmpty());
  }

  private Long persistNewMovie() {
    return this.persistNewMovie(false);
  }

  private Long persistNewMovie(boolean includePresentation) {
    var resource = new MovieResource();
    var dto = new NewMovieDto();
    dto.setName("Captain Marvel");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategoryId(createPriceCategory().getId());
    dto.setImageURL("about:blank");

    var response = resource.newMovie(dto);
    var createdId = (Long) response.getEntity();

    if (includePresentation) {
      var presentationRepo = RepositoryFactory.createRepository(Presentation.class);
      var presentation = new Presentation();
      var tomorrow = new Date(new Date().getTime() + 86_400_000);
      presentation.setDate(tomorrow);
      presentation.setMovie(RepositoryFactory.createRepository(Movie.class).find(createdId));
      presentationRepo.add(presentation);
    }

    return createdId;
  }

  private PriceCategoryDto createPriceCategory() {
    var priceCategory = new PriceCategoryDto();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }
}
