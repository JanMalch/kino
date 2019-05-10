package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.ThrowableProblem;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteMyReservationByIdControlTest {

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
    var presentation = util.provideReservationSetup("first", 5, "my@account.com");
    var reservation = util.provideNewReservation("my@account.com", presentation.getId());

    var control = new DeleteMyReservationByIdControl(reservation.getId(), "my@account.com");
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    assertNull(reservationRepository.find(reservation.getId()));
  }

  @Test
  public void testExecuteInvalid() {
    var presentation = util.provideReservationSetup("first", 5, "my@account.com");
    var reservation = util.provideNewReservation("my@account.com", presentation.getId());

    var control = new DeleteMyReservationByIdControl(reservation.getId(), "your@account.com");

    assertThrows(ThrowableProblem.class, () -> control.execute(new EitherResultBuilder<>()));
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    assertNotNull(reservationRepository.find(reservation.getId()));
  }
}
