package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetReservationByIdControlTest {

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
  public void testExecuteValid() {
    var reservation = util.provideNewReservation();
    var control = new GetReservationByIdControl(reservation.getId());
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservationDto = result.getSuccess().getData();
    assertEquals(reservation.getId(), reservationDto.getId());
    assertEquals(reservation.getPresentation().getId(), reservationDto.getPresentationId());
    assertEquals(reservation.getSeats().size(), reservationDto.getSeats().size());
    assertEquals(reservation.getReservationDate(), reservationDto.getReservationDate());
  }
}
