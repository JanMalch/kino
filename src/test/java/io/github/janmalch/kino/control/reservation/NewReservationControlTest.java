package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.reservation.ReservationDto;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NewReservationControlTest {

  private ReservationTestUtil util;
  private String accountName = "new@reservation.com";
  private String cinemaHallName = "first";
  private Presentation presentation;
  private int numberOfSeats = 5;

  @BeforeEach
  public void setUp() {
    util = new ReservationTestUtil();
    presentation = util.provideReservationSetup(cinemaHallName, numberOfSeats, accountName);
  }

  private Set<Long> getSeatIds(int dtoSeatIdAmount, List<Seat> seats) {
    return seats.stream().limit(dtoSeatIdAmount).map(Seat::getId).collect(Collectors.toSet());
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
    dto.setPresentationId(presentation.getId());
    dto.setSeatIds(getSeatIds(reservingSeatAmount, presentation.getCinemaHall().getSeats()));

    var control = new NewReservationControl(accountName, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservation = reservationRepository.find(result.getSuccess());
    assertEquals(reservingSeatAmount, reservation.getSeats().size());

    var seatRepository = RepositoryFactory.createRepository(Seat.class);

    var updatedSeats =
        dto.getSeatIds().stream().map(seatRepository::find).collect(Collectors.toList());
    updatedSeats.forEach(seat -> assertEquals(1, seat.getReservations().size()));
  }

  @Test
  public void execute_Invalid_SeatId_OutOfRange() {
    var seats = presentation.getCinemaHall().getSeats();

    // create Seat ID Set with one ID out of Range
    var seatIds = getSeatIds(seats.size(), seats);
    seatIds = seatIds.stream().map(id -> id += 1).collect(Collectors.toSet());

    var dto = new ReservationDto();
    dto.setPresentationId(presentation.getId());
    dto.setSeatIds(seatIds);

    var control = new NewReservationControl(accountName, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }

  @Test
  public void execute_Invalid_SeatId_AlreadyTaken() {
    var reservingSeatAmount = 3;

    var dto = new ReservationDto();
    dto.setPresentationId(presentation.getId());
    dto.setSeatIds(getSeatIds(reservingSeatAmount, presentation.getCinemaHall().getSeats()));

    var control = new NewReservationControl(accountName, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    control = new NewReservationControl(accountName, dto);
    result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }
}
