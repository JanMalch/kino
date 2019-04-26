package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReservationRepositoryTest {

  @Test
  void reservationWithSeats() {
    // prepare seats
    var exampleSeat = new Seat();
    var allSeats = Set.of(exampleSeat, new Seat(), new Seat());
    // persist seats
    var seatRepository = new SeatRepository();
    seatRepository.add(allSeats);
    // check if seat has been added
    var seatId = exampleSeat.getId();
    var seat = seatRepository.find(seatId);
    assertEquals(0, seat.getReservations().size());

    // create reservation with the given 2 seats
    var reservation = new Reservation();
    reservation.setSeats(allSeats);
    // persist reservation
    var reservationRepository = new ReservationRepository();
    reservationRepository.add(reservation);

    // refresh seat repository
    seatRepository = new SeatRepository();
    var freshSeat = seatRepository.find(seatId);
    assertEquals(1, freshSeat.getReservations().size(), "ManyToMany relationship failed");
  }
}
