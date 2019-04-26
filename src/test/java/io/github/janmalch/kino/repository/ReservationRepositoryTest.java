package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ReservationRepositoryTest {

  @Test
  void reservationWithSeats() {
    // add seats
    var allSeats = Set.of(new Seat(), new Seat());
    var seatRepository = new SeatRepository();
    seatRepository.add(allSeats);
    // check if seat has been added
    var seat = seatRepository.find(1);
    assertEquals(0, seat.getReservations().size());

    // create reservation with the given 2 seats
    var reservation = new Reservation();
    reservation.setSeats(allSeats);
    // persist reservation
    var reservationRepository = new ReservationRepository();
    reservationRepository.add(reservation);

    // refresh seat repository
    seatRepository = new SeatRepository();
    var freshSeat = seatRepository.find(1);
    assertEquals(1, freshSeat.getReservations().size());
  }
}
