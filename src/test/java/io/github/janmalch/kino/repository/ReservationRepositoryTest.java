package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ReservationRepositoryTest {

  @Test
  public void testReservationWithSeats() {
    Seat seat1 = new Seat();
    Seat seat2 = new Seat();

    SeatRepository seatRepository = new SeatRepository();
    seatRepository.add(seat1);
    seatRepository.add(seat2);

    Seat seat = seatRepository.find(1);
    assertEquals(1, seat.getReservations().size());

    Set<Seat> seats = new HashSet<>();
    seats.add(seat1);
    seats.add(seat2);

    Reservation reservation = new Reservation();
    reservation.setSeats(seats);

    ReservationRepository reservationRepository = new ReservationRepository();
    reservationRepository.add(reservation);

    seat = seatRepository.find(1);
    assertEquals(1, seat.getReservations().size());
  }
}
