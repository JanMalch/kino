package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NewReservationControlTest {

  private final Repository<Presentation> presentationRepository =
      RepositoryFactory.createRepository(Presentation.class);
  private final Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);
  private final Repository<Account> accountRepository =
      RepositoryFactory.createRepository(Account.class);
  private final Repository<CinemaHall> cinemaHallRepository =
      RepositoryFactory.createRepository(CinemaHall.class);

  private final String MAIL = "example@mail.com";
  private final int NUMBER_OF_SEATS = 5;
  private CinemaHall cinemaHall;
  private Presentation presentation;
  private Set<Seat> seats;

  @BeforeEach
  public void setUp() {
    var account = new Account();
    account.setEmail(MAIL);
    accountRepository.add(account);

    cinemaHall = ReservationTestUtil.createCinemaHall("first");

    seats = ReservationTestUtil.createSeats(NUMBER_OF_SEATS);
    seats.forEach(s -> s.setCinemaHall(cinemaHall));
    cinemaHall.setSeats(new ArrayList<>(seats));

    presentation = ReservationTestUtil.createPresentation();
    presentation.setCinemaHall(cinemaHall);

    cinemaHallRepository.add(cinemaHall);
    seats.forEach(seatRepository::add);
    presentationRepository.add(presentation);

    cinemaHallRepository.getEntityManager().clear();
    seatRepository.getEntityManager().clear();
    presentationRepository.getEntityManager().clear();
    accountRepository.getEntityManager().clear();
  }

  private Set<Long> getSeatIds(int dtoSeatIdAmount) {
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
    dto.setSeatIds(getSeatIds(reservingSeatAmount));

    var control = new NewReservationControl(MAIL, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    var reservation = reservationRepository.find(result.getSuccess().getData());
    assertEquals(reservingSeatAmount, reservation.getSeats().size());

    var updatedSeats =
        dto.getSeatIds().stream().map(seatRepository::find).collect(Collectors.toList());
    updatedSeats.forEach(seat -> assertEquals(1, seat.getReservations().size()));
  }

  @Test
  public void execute_Invalid_SeatId_OutOfRange() {
    // create Seat ID Set with one ID out of Range
    var seatIds = getSeatIds(NUMBER_OF_SEATS);
    seatIds = seatIds.stream().map(id -> id += 1).collect(Collectors.toSet());

    var dto = new ReservationDto();
    dto.setPresentationId(presentation.getId());
    dto.setSeatIds(seatIds);

    var control = new NewReservationControl(MAIL, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }

  @Test
  public void execute_Invalid_SeatId_AlreadyTaken() {
    var reservingSeatAmount = 3;

    var dto = new ReservationDto();
    dto.setPresentationId(presentation.getId());
    dto.setSeatIds(getSeatIds(reservingSeatAmount));

    var control = new NewReservationControl(MAIL, dto);
    var result = control.execute(new EitherResultBuilder<>());

    assertTrue(result.isSuccess());

    control = new NewReservationControl(MAIL, dto);
    result = control.execute(new EitherResultBuilder<>());

    assertFalse(result.isSuccess());
  }
}
