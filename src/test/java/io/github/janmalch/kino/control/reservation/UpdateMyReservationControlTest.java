package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.problem.ThrowableProblem;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMyReservationControlTest {

  private ReservationTestUtil util;
  private String cinemaHallName = "first";
  private String accountName = "update@my.com";
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
  public void testExecuteUpdateMyReservation() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var updateReservationDto =
        util.getReservationDto(existingReservation, presentation.getCinemaHall().getSeats());

    var control =
        new UpdateMyReservationControl(
            accountName, existingReservation.getId(), updateReservationDto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());
  }

  @Test
  public void testInvalidExecuteUpdateMyReservation() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var updateReservationDto =
        util.getReservationDto(existingReservation, presentation.getCinemaHall().getSeats());

    var control =
        new UpdateMyReservationControl(
            "expected@fail.com", existingReservation.getId(), updateReservationDto);

    assertThrows(ThrowableProblem.class, () -> control.execute(new EitherResultBuilder<>()));
  }
}
