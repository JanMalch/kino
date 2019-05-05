package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Seat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class ReservationTestUtil {

  static CinemaHall createCinemaHall(String name) {
    CinemaHall cinemaHall = new CinemaHall();
    cinemaHall.setName(name);
    return cinemaHall;
  }

  static Seat createSeat(int seatNumber) {
    Seat seat = new Seat();
    seat.setSeatNumber(seatNumber);
    return seat;
  }

  static Set<Seat> createSeats(int number) {
    var seats = new HashSet<Seat>();
    for (int i = 0; i < number; i++) {
      seats.add(createSeat(i + 1));
    }
    return seats;
  }

  static Set<Long> getNumberOfSeatIds(int number, Set<Seat> seats) {
    return seats.stream().limit(number).map(Seat::getId).collect(Collectors.toSet());
  }

  static Presentation createPresentation() {
    return new Presentation();
  }
}
