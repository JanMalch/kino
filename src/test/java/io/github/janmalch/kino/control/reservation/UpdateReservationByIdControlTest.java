package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateReservationByIdControlTest {

  private ReservationTestUtil util;
  private String cinemaHallName = "first";
  private String accountName = "update@byid.com";
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
  public void executeValid() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var updateReservationDto =
        util.getReservationDto(existingReservation, presentation.getCinemaHall().getSeats());

    var updateControl =
        new UpdateReservationByIdControl(
            existingReservation.getId(), accountName, Role.MODERATOR, updateReservationDto);
    var updateResult = updateControl.execute(new EitherResultBuilder<>());

    assertTrue(updateResult.isSuccess());

    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);

    var updatedReservation = reservationRepository.find(existingReservation.getId());
    assertEquals(existingReservation.getId(), updatedReservation.getId());
    assertNotEquals(existingReservation.getSeats(), updatedReservation.getSeats());
  }

  @Test
  public void executeInvalidCustomer() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var updateReservationDto =
        util.getReservationDto(existingReservation, presentation.getCinemaHall().getSeats());

    var updateControl =
        new UpdateReservationByIdControl(
            existingReservation.getId(), "invalidCustomer", Role.CUSTOMER, updateReservationDto);
    var updateResult = updateControl.execute(new EitherResultBuilder<>());

    assertTrue(updateResult.isFailure());
  }

  @Test
  public void executeInvalidUpdateSeatDto() {
    var existingReservation = util.provideNewReservation(accountName, presentation.getId());
    var seats = presentation.getCinemaHall().getSeats();
    var seatIds = seats.stream().limit(seats.size()).map(Seat::getId).collect(Collectors.toSet());
    var invalidSeatIds = seatIds.stream().map(id -> id += 1).collect(Collectors.toSet());

    var updateReservationDto = new ReservationDto();
    updateReservationDto.setSeatIds(invalidSeatIds);
    updateReservationDto.setPresentationId(presentation.getId());

    var updateControl =
        new UpdateReservationByIdControl(
            existingReservation.getId(), accountName, Role.MODERATOR, updateReservationDto);
    var updateResult = updateControl.execute(new EitherResultBuilder<>());

    assertTrue(updateResult.isFailure());
  }
}
