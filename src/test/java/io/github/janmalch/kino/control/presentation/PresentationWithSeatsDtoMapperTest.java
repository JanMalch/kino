package io.github.janmalch.kino.control.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.Date;
import org.junit.jupiter.api.Test;

class PresentationWithSeatsDtoMapperTest {

  @Test
  void map() {
    var cinemaHall = new CinemaHall();
    cinemaHall.setName("Saal 1");
    RepositoryFactory.createRepository(CinemaHall.class).add(cinemaHall);

    var movie = new Movie();
    RepositoryFactory.createRepository(Movie.class).add(movie);

    var entity = new Presentation();
    entity.setDate(new Date());
    entity.setCinemaHall(cinemaHall);
    entity.setMovie(movie);

    RepositoryFactory.createRepository(Presentation.class).add(entity);

    var mapper = new PresentationWithSeatsDtoMapper();
    var dto = mapper.map(entity);

    assertEquals(entity.getId(), dto.getId());
    assertEquals(entity.getCinemaHall().getId(), dto.getCinemaHallId());
  }
}
