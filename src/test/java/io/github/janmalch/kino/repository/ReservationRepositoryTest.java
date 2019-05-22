package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import java.util.Set;
import org.junit.jupiter.api.Test;

// TODO: was wird hier effektiv getestet?
class ReservationRepositoryTest {

  @Test
  void reservationWithSeats() {
    // prepare seats
    var exampleSeat = new Seat();
    var allSeats = Set.of(exampleSeat, new Seat(), new Seat());
    // persist seats
    var seatRepository = RepositoryFactory.createRepository(Seat.class);
    seatRepository.add(allSeats);
    // check if seat has been added
    var seatId = exampleSeat.getId();
    var seat = seatRepository.find(seatId);
    assertEquals(0, seat.getReservations().size());

    // create reservation with the given 2 seats
    var reservation = new Reservation();
    reservation.setSeats(allSeats);
    // persist reservation
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    reservationRepository.add(reservation);

    // refresh seat repository
    seatRepository = RepositoryFactory.createRepository(Seat.class);
    var freshSeat = seatRepository.find(seatId);
    assertEquals(1, freshSeat.getReservations().size(), "ManyToMany relationship failed");
  }
}
