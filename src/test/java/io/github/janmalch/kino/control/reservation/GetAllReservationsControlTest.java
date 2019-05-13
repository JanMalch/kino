package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetAllReservationsControlTest {

  private ReservationTestUtil util;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void testExecute() {
    var cinemaHallNames = Arrays.asList("first", "second", "third");
    var numberOfSeats = 10;
    var accountnames = Arrays.asList("my@account.com", "your@account.com", "her@account.com");
    var presentations = new ArrayList<Presentation>();
    var reservations = new ArrayList<Reservation>();

    for (int i = 0; i < cinemaHallNames.size(); i++) {
      presentations.add(
          util.provideReservationSetup(cinemaHallNames.get(i), numberOfSeats, accountnames.get(i)));
      reservations.add(
          util.provideNewReservation(accountnames.get(i), presentations.get(i).getId()));
    }

    var control = new GetAllReservationsControl();
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservationDtos = result.getSuccess();
    assertEquals(reservations.size(), reservationDtos.size());
  }
}
