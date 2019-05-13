package io.github.janmalch.kino.control.cinemahall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Seat;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CinemaHallDtoMapperTest {

  @Test
  void map() {
    var entity = new CinemaHall();
    entity.setId(1L);
    entity.setName("Saal 1");

    var seatEntity = new Seat();
    seatEntity.setId(1L);
    seatEntity.setRow("A");
    seatEntity.setSeatNumber(1);
    seatEntity.setReservations(Set.of());
    seatEntity.setCinemaHall(entity);
    entity.setSeats(List.of(seatEntity));

    var mapper = new CinemaHallDtoMapper();
    var dto = mapper.map(entity);
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getSeats().size(), dto.getSeats().size());
  }
}
