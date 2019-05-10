package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.PriceCategory;
import org.junit.jupiter.api.Test;

class ReflectionMapperTest {

  @Test
  void map() {
    var mapper = new ReflectionMapper<Movie, MovieDto>(MovieDto.class);
    var entity = new Movie();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setPriceCategory(createNormalPriceCategory());
    entity.setDuration(2.5F);

    var domain = mapper.map(entity);
    assertEquals("Captain Marvel", domain.getName());
  }

  @Test
  void update() {
    var mapper = new ReflectionMapper<Movie, MovieDto>(MovieDto.class);

    var entity = new Movie();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setPriceCategory(createNormalPriceCategory());
    entity.setDuration(2.5F);

    var domain = mapper.map(entity);
    domain.setName("Wonder Woman");
    domain.setPriceCategory(createOverlongPriceCategory());

    var reverseMapper = new ReflectionMapper<MovieDto, Movie>(Movie.class);
    var newEntity = reverseMapper.update(domain, entity);
    assertEquals(1L, newEntity.getId());
    assertEquals("Wonder Woman", newEntity.getName());
    assertEquals(
        createOverlongPriceCategory(),
        newEntity.getPriceCategory(),
        "Empty values should not be used");
  }

  private PriceCategory createNormalPriceCategory() {
    var priceCategory = new PriceCategory();
    priceCategory.setName("normal");
    priceCategory.setRegularPrice(9.99f);
    priceCategory.setReducedPrice(7.99f);
    return priceCategory;
  }

  private PriceCategoryDto createOverlongPriceCategory() {
    var priceCategory = new PriceCategoryDto();
    priceCategory.setName("overlong");
    priceCategory.setRegularPrice(12.99f);
    priceCategory.setReducedPrice(10.99f);
    return priceCategory;
  }
}
