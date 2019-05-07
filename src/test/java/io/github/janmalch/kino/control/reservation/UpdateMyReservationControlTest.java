package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMyReservationControlTest {

  private ReservationTestUtil util;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
  }

  @Test
  public void testExecuteUpdateMyReservation() {
    var existingReservation = util.provideNewReservation();
    var updateReservationDto = util.getReservationDto(existingReservation);

    var control =
        new UpdateMyReservationControl(
            util.getMail(), existingReservation.getId(), updateReservationDto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());
  }

  @Test
  public void testInvalidExecuteUpdateMyReservation() {
    var existingReservation = util.provideNewReservation();
    var updateReservationDto = util.getReservationDto(existingReservation);

    var control =
        new UpdateMyReservationControl(
            "expected@fail.com", existingReservation.getId(), updateReservationDto);

    try {
      control.execute(new EitherResultBuilder<>());
      fail();
    } catch (Exception problem) {
      assertNotNull(problem);
    }
  }
}
