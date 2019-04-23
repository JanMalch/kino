package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.entity.Movie;
import org.junit.jupiter.api.Test;

class ReflectionMapperTest {

  @Test
  void map() {
    var mapper = new ReflectionMapper();
    var entity = new Movie();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setPriceCategory("1");
    entity.setDuration(2.5F);

    var domain = mapper.map(entity, MovieDto.class);
    assertEquals("Captain Marvel", domain.getName());
  }

  @Test
  void update() {
    var mapper = new ReflectionMapper();

    var entity = new Movie();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setPriceCategory("1");
    entity.setDuration(2.5F);

    var domain = mapper.map(entity, MovieDto.class);
    domain.setName("Wonder Woman");
    domain.setPriceCategory("");

    var newEntity = mapper.update(domain, entity, Movie.class);
    assertEquals(1L, newEntity.getId());
    assertEquals("Wonder Woman", newEntity.getName());
    assertEquals("1", newEntity.getPriceCategory(), "Empty values should not be used");
  }
}
