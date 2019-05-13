package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetReservationByIdControlTest {

  private ReservationTestUtil util;
  private String cinemaHallName = "first";
  private String accountName = "get@byid.com";
  private Presentation presentation;
  private int numberOfSeats = 5;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
    presentation = util.provideReservationSetup(cinemaHallName, numberOfSeats, accountName);
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void executeValidCustomer() {

    var reservation = util.provideNewReservation(accountName, presentation.getId());
    var control = new GetReservationByIdControl(reservation.getId(), Role.CUSTOMER, accountName);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservationDto = result.getSuccess();
    assertEquals(reservation.getId(), reservationDto.getId());
    assertEquals(reservation.getPresentation().getId(), reservationDto.getPresentationId());
    assertEquals(reservation.getSeats().size(), reservationDto.getSeats().size());
    assertEquals(reservation.getReservationDate(), reservationDto.getReservationDate());
  }

  @Test
  public void executeInvalidCustomer() {

    var reservation = util.provideNewReservation(accountName, presentation.getId());
    var control =
        new GetReservationByIdControl(reservation.getId(), Role.CUSTOMER, "invalid@customer.com");
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isFailure());
  }

  @Test
  public void executeValidModerator() {

    var reservation = util.provideNewReservation(accountName, presentation.getId());
    var control =
        new GetReservationByIdControl(reservation.getId(), Role.MODERATOR, "valid@moderator.com");
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservationDto = result.getSuccess();
    assertEquals(reservation.getId(), reservationDto.getId());
    assertEquals(reservation.getPresentation().getId(), reservationDto.getPresentationId());
    assertEquals(reservation.getSeats().size(), reservationDto.getSeats().size());
    assertEquals(reservation.getReservationDate(), reservationDto.getReservationDate());
  }
}
