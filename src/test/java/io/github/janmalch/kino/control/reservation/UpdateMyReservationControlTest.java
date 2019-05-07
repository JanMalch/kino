package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.problem.ThrowableProblem;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMyReservationControlTest {

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

    assertThrows(ThrowableProblem.class, () -> control.execute(new EitherResultBuilder<>()));
  }
}
