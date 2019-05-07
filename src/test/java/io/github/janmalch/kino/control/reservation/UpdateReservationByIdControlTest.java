package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateReservationByIdControlTest {

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
  public void testExecute_Valid() {

    var existingReservation = util.provideNewReservation();
    var updateReservationDto = util.getReservationDto(existingReservation);

    var updateControl =
        new UpdateReservationByIdControl(existingReservation.getId(), updateReservationDto);
    var updateResult = updateControl.execute(new EitherResultBuilder<>());

    assertTrue(updateResult.isSuccess());

    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);

    var updatedReservation = reservationRepository.find(existingReservation.getId());
    assertEquals(existingReservation.getId(), updatedReservation.getId());
    assertNotEquals(existingReservation.getSeats(), updatedReservation.getSeats());
  }
}
