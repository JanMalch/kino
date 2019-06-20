package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.movie.NewMovieDto;
import io.github.janmalch.kino.api.model.pricecategory.PriceCategoryDto;
import org.junit.jupiter.api.Test;

class NewMovieMapperTest {

  @Test
  void map() {
    var dto = new NewMovieDto();
    dto.setName("Captain Marvel");
    dto.setPriceCategoryId(createPriceCategory().getId());
    dto.setAgeRating(12);
    dto.setDuration(2.5F);

    var mapper = new NewMovieControl.NewMovieMapper();
    var result = mapper.map(dto);
    assertEquals("Captain Marvel", result.getName());
    assertEquals(12, result.getAgeRating());
    assertEquals(2.5F, result.getDuration());
  }

  private PriceCategoryDto createPriceCategory() {
    var priceCategory = new PriceCategoryDto();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }
}
