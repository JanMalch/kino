package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.seat.SeatForPresentationDto;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetSeatsWithStatusControlTest {

  private CinemaHall cinemaHall;

  @BeforeEach
  void setup() {
    cinemaHall = new CinemaHall();
    var repository = RepositoryFactory.createRepository(CinemaHall.class);
    repository.add(cinemaHall);
  }

  @Test
  void execute() {
    var presentationId = setupPresentation();
    var control = new GetSeatsWithStatusControl(presentationId);
    var either = control.execute(new EitherResultBuilder<>());
    var result = either.getSuccess();

    var expectedTaken = result.stream().filter(SeatForPresentationDto::isTaken).count();
    assertEquals(2, expectedTaken);
  }

  private long setupPresentation() {
    var repository = RepositoryFactory.createRepository(Presentation.class);
    var presentation = new Presentation();
    presentation.setCinemaHall(cinemaHall);
    repository.add(presentation);

    setupReservations(presentation);

    return presentation.getId();
  }

  private void setupReservations(Presentation presentation) {
    var seats = setupSeats();

    var repository = RepositoryFactory.createRepository(Reservation.class);
    var reservation = new Reservation();

    // the setup only returns 2 of the 4 persisted seats
    reservation.setSeats(seats);
    reservation.setPresentation(presentation);
    repository.add(reservation);
  }

  private Set<Seat> setupSeats() {
    var repository = RepositoryFactory.createRepository(Seat.class);

    var seatA1 = new Seat();
    seatA1.setRow("A");
    seatA1.setSeatNumber(1);
    var seatA2 = new Seat();
    seatA2.setRow("A");
    seatA2.setSeatNumber(2);
    var seatB1 = new Seat();
    seatB1.setRow("B");
    seatB1.setSeatNumber(1);
    var seatB2 = new Seat();
    seatB2.setRow("B");
    seatB2.setSeatNumber(2);

    var set = Set.of(seatA1, seatA2, seatB1, seatB2);
    set.forEach(s -> s.setCinemaHall(cinemaHall));
    repository.add(set);
    return Set.of(seatA1, seatA2);
  }
}
