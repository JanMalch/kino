package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeleteReservationByIdControlTest {

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
    var presentation = util.provideReservationSetup("first", 10, "my@account.com");
    var reservation = util.provideNewReservation("my@account.com", presentation.getId());
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);

    var control =
        new DeleteReservationByIdControl(reservation.getId(), "my@account.com", Role.MODERATOR);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());
    assertNull(reservationRepository.find(reservation.getId()));
  }

  @Test
  public void testExecuteInvalidCustomer() {
    var presentation = util.provideReservationSetup("first", 10, "my@account.com");
    var reservation = util.provideNewReservation("my@account.com", presentation.getId());

    var control =
        new DeleteReservationByIdControl(reservation.getId(), "invalid@account.com", Role.CUSTOMER);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isFailure());
  }
}
