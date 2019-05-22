package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.CinemaHallDto;
import io.github.janmalch.kino.api.model.cinemahall.NewCinemaHallDto;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinemaHallResourceTest {

  @BeforeEach
  void setup() {
    new EntityWiper().wipeDB();
  }

  @Test
  void getAllCinemaHalls() {
    // uses GetEntitiesControl internally, check if return type is correct
    var resource = new CinemaHallResource();
    var result = (List<CinemaHallDto>) resource.getAllCinemaHalls().getEntity();
    assertNotNull(result);
  }

  @Test
  void deleteCinemaHall() {
    var id = persistCinemaHall();

    var persistedSeats = RepositoryFactory.createRepository(Seat.class).findAll();
    assertTrue(persistedSeats.size() > 0);

    var resource = new CinemaHallResource();
    var result = resource.deleteCinemaHall(id);
    assertNotNull(result);

    persistedSeats = RepositoryFactory.createRepository(Seat.class).findAll();
    assertTrue(persistedSeats.isEmpty());
  }

  @Test
  void createCinemaHall() {
    persistCinemaHall();
  }

  private long persistCinemaHall() {
    var resource = new CinemaHallResource();
    var dto = new NewCinemaHallDto();
    dto.setName("Testsaal");
    dto.setRowCount(5);
    dto.setSeatsPerRow(8);
    var result = resource.createCinemaHall(dto);
    assertNotNull(result);

    var id = (long) result.getEntity();

    var persistedEntity = RepositoryFactory.createRepository(CinemaHall.class).find(id);

    assertNotNull(persistedEntity);
    assertEquals(8 * 5, persistedEntity.getSeats().size());

    return id;
  }
}
