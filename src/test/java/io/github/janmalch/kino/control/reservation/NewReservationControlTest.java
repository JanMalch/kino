package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NewReservationControlTest {

  private ReservationTestUtil util;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
  }

  private Set<Long> getSeatIds(int dtoSeatIdAmount) {
    return util.getSeats()
        .stream()
        .limit(dtoSeatIdAmount)
        .map(Seat::getId)
        .collect(Collectors.toSet());
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void execute_Valid() {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);

    var reservingSeatAmount = 3;

    var dto = new ReservationDto();
    dto.setPresentationId(util.getPresentation().getId());
    dto.setSeatIds(getSeatIds(reservingSeatAmount));

    var control = new NewReservationControl(util.getMail(), dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservation = reservationRepository.find(result.getSuccess().getData());
    assertEquals(reservingSeatAmount, reservation.getSeats().size());

    var updatedSeats =
        dto.getSeatIds().stream().map(util.getSeatRepository()::find).collect(Collectors.toList());
    updatedSeats.forEach(seat -> assertEquals(1, seat.getReservations().size()));
  }

  @Test
  public void execute_Invalid_SeatId_OutOfRange() {
    // create Seat ID Set with one ID out of Range
    var seatIds = getSeatIds(util.getNumberOfSeats());
    seatIds = seatIds.stream().map(id -> id += 1).collect(Collectors.toSet());

    var dto = new ReservationDto();
    dto.setPresentationId(util.getPresentation().getId());
    dto.setSeatIds(seatIds);

    var control = new NewReservationControl(util.getMail(), dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }

  @Test
  public void execute_Invalid_SeatId_AlreadyTaken() {
    var reservingSeatAmount = 3;

    var dto = new ReservationDto();
    dto.setPresentationId(util.getPresentation().getId());
    dto.setSeatIds(getSeatIds(reservingSeatAmount));

    var control = new NewReservationControl(util.getMail(), dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    control = new NewReservationControl(util.getMail(), dto);
    result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }
}
