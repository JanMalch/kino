package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationTestUtil {

  public Presentation provideReservationSetup(
      String cinemaHallName, int numberOfSeats, String accountMail) {
    var cinemaHall = provideExistingCinemaHallWithSeats(cinemaHallName, numberOfSeats);
    provideExistingAccount(accountMail);
    return provideExistingPresentationForCinemaHall(cinemaHall);
  }

  public ReservationDto getReservationDto(Reservation existingReservation, List<Seat> seats) {
    var updateSeatIds = seats.stream().map(Seat::getId).collect(Collectors.toSet());
    var reservedSeatIds =
        existingReservation.getSeats().stream().map(Seat::getId).collect(Collectors.toSet());

    updateSeatIds.removeAll(reservedSeatIds);

    var updateReservationDto = new ReservationDto();
    updateReservationDto.setSeatIds(updateSeatIds);
    updateReservationDto.setPresentationId(existingReservation.getPresentation().getId());

    return updateReservationDto;
  }

  public ReservationDto provideNewReservationDto(long presentationId) {
    var presentation = RepositoryFactory.createRepository(Presentation.class).find(presentationId);

    var newReservationDto = new ReservationDto();
    newReservationDto.setSeatIds(getNumberOfSeatIds(3, presentation.getCinemaHall().getSeats()));
    newReservationDto.setPresentationId(presentation.getId());

    return newReservationDto;
  }

  public Reservation provideNewReservation(String accountName, long presentationId) {
    var presentation = RepositoryFactory.createRepository(Presentation.class).find(presentationId);

    var newReservationDto = new ReservationDto();
    newReservationDto.setSeatIds(getNumberOfSeatIds(3, presentation.getCinemaHall().getSeats()));
    newReservationDto.setPresentationId(presentation.getId());

    var newControl = new NewReservationControl(accountName, newReservationDto);
    var newResult = newControl.execute(new EitherResultBuilder<>());

    assertTrue(newResult.isSuccess());

    return RepositoryFactory.createRepository(Reservation.class)
        .find(newResult.getSuccess().getData());
  }

  private void provideExistingSeats(int numberOfSeats, CinemaHall cinemaHall) {
    var seats = createSeats(numberOfSeats);
    seats.forEach(s -> s.setCinemaHall(cinemaHall));
    RepositoryFactory.createRepository(Seat.class).add(seats);
  }

  private CinemaHall provideExistingCinemaHallWithSeats(String name, int numberOfSeats) {
    var cinemaHall = createCinemaHall(name);
    RepositoryFactory.createRepository(CinemaHall.class).add(cinemaHall);
    provideExistingSeats(numberOfSeats, cinemaHall);

    return cinemaHall;
  }

  private void provideExistingAccount(String accountMail) {
    var account = new Account();
    account.setEmail(accountMail);
    RepositoryFactory.createRepository(Account.class).add(account);
  }

  private Presentation provideExistingPresentationForCinemaHall(CinemaHall cinemaHall) {
    var presentation = new Presentation();
    presentation.setCinemaHall(cinemaHall);
    var repo = RepositoryFactory.createRepository(Presentation.class);
    repo.add(presentation);

    // Getting presentation directly from database, containing cinemaHall with Seats
    repo.getEntityManager().clear();
    return repo.find(presentation.getId());
  }

  private CinemaHall createCinemaHall(String name) {
    CinemaHall cinemaHall = new CinemaHall();
    cinemaHall.setName(name);
    return cinemaHall;
  }

  private Seat createSeat(int seatNumber) {
    Seat seat = new Seat();
    seat.setSeatNumber(seatNumber);
    return seat;
  }

  private Set<Seat> createSeats(int number) {
    var seats = new HashSet<Seat>();
    for (int i = 0; i < number; i++) {
      seats.add(createSeat(i + 1));
    }
    return seats;
  }

  private Set<Long> getNumberOfSeatIds(int number, List<Seat> seats) {
    return seats.stream().limit(number).map(Seat::getId).collect(Collectors.toSet());
  }
}
