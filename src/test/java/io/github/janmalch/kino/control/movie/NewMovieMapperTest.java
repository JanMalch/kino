package io.github.janmalch.kino.control.movie;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.MovieDto;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

class NewMovieMapperTest {

  @Test
  void mapToEntity() {
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setPriceCategory("1");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setStartDate("20g20-01-01");
    dto.setEndDate("20g20-01-02");

    var mapper = new NewMovieControl.NewMovieMapper();
    try {
      mapper.mapToEntity(dto);
      fail("Should not be able to validate without Exception");
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof ParseException)) {
        fail("Unknown RuntimeException thrown");
      }
      // ParseException is expected here
    }
  }
}
