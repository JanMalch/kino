package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.api.model.PriceCategoryDto;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class NewMovieMapperTest {

  @Test
  void map() {
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setPriceCategory(createPriceCategory());
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setStartDate("20g20-01-01");
    dto.setEndDate("20g20-01-02");

    var mapper = new NewMovieControl.NewMovieMapper();
    try {
      mapper.map(dto);
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
