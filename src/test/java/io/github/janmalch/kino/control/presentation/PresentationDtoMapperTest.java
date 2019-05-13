package io.github.janmalch.kino.control.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Presentation;
import java.util.Date;
import org.junit.jupiter.api.Test;

class PresentationDtoMapperTest {

  @Test
  void map() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setId(1L);

    var entity = new Presentation();
    entity.setId(1L);
    entity.setDate(new Date());
    entity.setCinemaHall(cinemaHall);

    var mapper = new PresentationDtoMapper();
    var dto = mapper.map(entity);

    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCinemaHall().getId(), dto.getCinemaHallId());
  }
}
