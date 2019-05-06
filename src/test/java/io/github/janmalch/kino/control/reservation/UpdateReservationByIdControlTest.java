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

public class UpdateReservationByIdControlTest {

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

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  private Long provideExistingReservationId() {
    var newReservationDto = new ReservationDto();
    newReservationDto.setSeatIds(ReservationTestUtil.getNumberOfSeatIds(3, seats));
    newReservationDto.setPresentationId(presentation.getId());

    var newControl = new NewReservationControl(MAIL, newReservationDto);
    var newResult = newControl.execute(new EitherResultBuilder<>());

    assertTrue(newResult.isSuccess());
    return newResult.getSuccess().getData();
  }

  @Test
  public void testExecute_Valid() {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var existingReservationId = provideExistingReservationId();
    var existingReservation = reservationRepository.find(existingReservationId);

    var updateSeatIds = seats.stream().map(Seat::getId).collect(Collectors.toSet());
    var reservedSeatIds =
        existingReservation.getSeats().stream().map(Seat::getId).collect(Collectors.toSet());

    updateSeatIds.removeAll(reservedSeatIds);

    var updateReservationDto = new ReservationDto();
    updateReservationDto.setSeatIds(updateSeatIds);
    updateReservationDto.setPresentationId(existingReservation.getPresentation().getId());

    var updateControl =
        new UpdateReservationByIdControl(existingReservation.getId(), updateReservationDto);
    var updateResult = updateControl.execute(new EitherResultBuilder<>());

    assertTrue(updateResult.isSuccess());

    reservationRepository.getEntityManager().clear();

    var updatedReservation = reservationRepository.find(existingReservation.getId());
    assertEquals(existingReservation.getId(), updatedReservation.getId());
    assertNotEquals(existingReservation.getSeats(), updatedReservation.getSeats());
  }
}
