package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.MovieDto;
import java.text.ParseException;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class NewMovieControlTest {

  @Test
  void executeInvalidDateRange() {
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("2020-02-02");
    dto.setEndDate("2019-01-01");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategory("1");

    var control = new NewMovieControl(dto);
    var response = control.execute(new ResponseResultBuilder<>());
    assertEquals(400, response.getStatus());
    var problem = (HashMap<String, Object>) response.getEntity(); // TODO: refactor when new mapper
    assertEquals("Start date is after the end date", problem.get("title"));
  }

  @Test
  void validateInvalidDateFormat() {
    var dto = new MovieDto();
    dto.setName("Captain Marvel");
    dto.setStartDate("20g20-02-02");
    dto.setEndDate("2019-01-01");
    dto.setAgeRating(12);
    dto.setDuration(2.5F);
    dto.setPriceCategory("1");

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
}
