package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.api.model.movie.NewMovieDto;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class NewMovieControlTest {

  @Test
  void executeInvalidDateRange() {
    var dto = new NewMovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("2020-02-02");
    dto.setEndDate("2019-01-01");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategoryId(createPriceCategory().getId());
    dto.setImageURL("about:blank");

    var control = new NewMovieControl(dto);
    var response = control.execute(new EitherResultBuilder<>());
    assertTrue(response.isFailure());
    assertEquals(400, response.getStatus().getStatusCode());
    var problem = response.getProblem();
    assertEquals("Start date is after the end date", problem.getTitle());
  }

  @Test
  void validateInvalidDateFormat() {
    var dto = new NewMovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("20g20-02-02");
    dto.setEndDate("2019-01-01");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategoryId(createPriceCategory().getId());
    dto.setImageURL("about:blank");

    var control = new NewMovieControl(dto);
    try {
      control.validate();
      fail("Should not be able to validate without Exception");
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof ParseException)) {
        fail("Unknown RuntimeException thrown");
      }
      // ParseException is expected here
    }
  }

  private PriceCategoryDto createPriceCategory() {
    var priceCategory = new PriceCategoryDto();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }
}
