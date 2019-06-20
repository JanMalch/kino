package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.movie.NewMovieDto;
import io.github.janmalch.kino.api.model.pricecategory.PriceCategoryDto;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.Test;

class NewMovieControlTest {

  @Test
  void executeInvalidDateRange() {
    var dto = new NewMovieDto();
    dto.setName("Captain Marvel");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategoryId(createPriceCategory().getId());
    dto.setImageURL("about:blank");

    var control = new NewMovieControl(dto);
    var response = control.execute(new EitherResultBuilder<>());
    assertTrue(response.isSuccess());
  }

  private PriceCategoryDto createPriceCategory() {
    var priceCategory = new PriceCategoryDto();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }
}
