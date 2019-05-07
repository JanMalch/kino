package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class ReservationTestUtil {

  private final Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);
  private final Repository<Account> accountRepository =
      RepositoryFactory.createRepository(Account.class);
  private final Repository<CinemaHall> cinemaHallRepository =
      RepositoryFactory.createRepository(CinemaHall.class);

  private String mail = "example@mail.com";
  private int numberOfSeats = 5;
  private CinemaHall cinemaHall;
  private Presentation presentation;
  private Set<Seat> seats;

  ReservationTestUtil() {
    var account = new Account();
    account.setEmail(mail);
    accountRepository.add(account);

    cinemaHall = createCinemaHall("first");

    seats = createSeats(numberOfSeats);
    seats.forEach(s -> s.setCinemaHall(cinemaHall));
    cinemaHall.setSeats(new ArrayList<>(seats));

    presentation = createPresentation();
    presentation.setCinemaHall(cinemaHall);

    cinemaHallRepository.add(cinemaHall);
    seats.forEach(seatRepository::add);
    Repository<Presentation> presentationRepository =
        RepositoryFactory.createRepository(Presentation.class);
    presentationRepository.add(presentation);

    cinemaHallRepository.getEntityManager().clear();
    seatRepository.getEntityManager().clear();
    presentationRepository.getEntityManager().clear();
    accountRepository.getEntityManager().clear();
  }

  ReservationDto getReservationDto(Reservation existingReservation) {
    var updateSeatIds = getSeats().stream().map(Seat::getId).collect(Collectors.toSet());
    var reservedSeatIds =
        existingReservation.getSeats().stream().map(Seat::getId).collect(Collectors.toSet());

    updateSeatIds.removeAll(reservedSeatIds);

    var updateReservationDto = new ReservationDto();
    updateReservationDto.setSeatIds(updateSeatIds);
    updateReservationDto.setPresentationId(existingReservation.getPresentation().getId());

    return updateReservationDto;
  }

  Reservation provideNewReservation() {
    var newReservationDto = new ReservationDto();
    newReservationDto.setSeatIds(getNumberOfSeatIds(3, seats));
    newReservationDto.setPresentationId(presentation.getId());

    var newControl = new NewReservationControl(mail, newReservationDto);
    var newResult = newControl.execute(new EitherResultBuilder<>());

    assertTrue(newResult.isSuccess());

    return RepositoryFactory.createRepository(Reservation.class)
        .find(newResult.getSuccess().getData());
  }

  CinemaHall createCinemaHall(String name) {
    CinemaHall cinemaHall = new CinemaHall();
    cinemaHall.setName(name);
    return cinemaHall;
  }

  Seat createSeat(int seatNumber) {
    Seat seat = new Seat();
    seat.setSeatNumber(seatNumber);
    return seat;
  }

  Set<Seat> createSeats(int number) {
    var seats = new HashSet<Seat>();
    for (int i = 0; i < number; i++) {
      seats.add(createSeat(i + 1));
    }
    return seats;
  }

  Set<Long> getNumberOfSeatIds(int number, Set<Seat> seats) {
    return seats.stream().limit(number).map(Seat::getId).collect(Collectors.toSet());
  }

  Presentation createPresentation() {
    return new Presentation();
  }

  Set<Seat> getSeats() {
    return this.seats;
  }

  public String getMail() {
    return mail;
  }

  public int getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(int numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public Presentation getPresentation() {
    return this.presentation;
  }

  public Repository<Seat> getSeatRepository() {
    return this.seatRepository;
  }
}
