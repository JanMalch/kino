package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.janmalch.kino.api.model.CinemaHallDto;
import java.util.List;
import org.junit.jupiter.api.Test;

class CinemaHallResourceTest {

  @Test
  void getAllCinemaHalls() {
    // uses GetEntitiesControl internally, check if return type is correct
    var resource = new CinemaHallResource();
    var result = (List<CinemaHallDto>) resource.getAllCinemaHalls().getEntity();
    assertNotNull(result);
  }
}
