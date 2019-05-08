package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.problem.ThrowableProblem;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetMyReservationsControlTest {

  private String cinemaHallName = "first";
  private ReservationTestUtil util;
  private String accountName = "get@my.com";
  private Presentation presentation;
  private int numberOfSeats = 5;

  @BeforeEach
  void setUp() {
    util = new ReservationTestUtil();
    presentation = util.provideReservationSetup(cinemaHallName, numberOfSeats, accountName);
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void testExecuteValid() {

    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var control = new GetMyReservationsControl(existingReservation.getId(), accountName);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var infoDto = result.getSuccess().getData();
    assertEquals(existingReservation.getId(), infoDto.getId());
    assertEquals(existingReservation.getPresentation().getId(), infoDto.getPresentationId());
    assertEquals(existingReservation.getSeats().size(), infoDto.getSeats().size());
    assertEquals(existingReservation.getReservationDate(), infoDto.getReservationDate());
  }

  @Test
  public void testExecuteInvalid() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var control = new GetMyReservationsControl(existingReservation.getId(), "fail@expected.com");

    assertThrows(ThrowableProblem.class, () -> control.execute(new EitherResultBuilder<>()));
  }
}
