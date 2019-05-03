package io.github.janmalch.kino.control.reservation;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.entity.*;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.HashSet;
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

  private final String mail = "example@mail.com";
  private final int reservingSeatAmount = 3;

  private Set<Long> seatIds = new HashSet<>();
  private long presentationId;

  private Set<Long> getSeatIds(int numberOfSeats, int dtoSeatIdAmount) {
    Set<Seat> seats = new HashSet<>();
    for (int i = 0; i < numberOfSeats; i++) {
      seats.add(new Seat());
    }
    seatRepository.add(seats);
    return seats.stream().limit(dtoSeatIdAmount).map(Seat::getId).collect(Collectors.toSet());
  }

  @BeforeEach
  public void setUp() {
    seatIds = getSeatIds(6, reservingSeatAmount);
    Presentation presentation = new Presentation();
    presentationRepository.add(presentation);
    presentationId = presentation.getId();

    var account = new Account();
    account.setEmail(mail);
    accountRepository.add(account);
  }

  @AfterEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  public void executeValid() {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var dto = new ReservationDto();

    dto.setPresentationId(presentationId);
    dto.setSeatIds(seatIds);

    var control = new NewReservationControl(mail, dto);
    var result = control.execute(new EitherResultBuilder<>());
    var reservation = reservationRepository.find(result.getSuccess().getData());

    assertTrue(result.isSuccess());
    assertEquals(reservingSeatAmount, reservation.getSeats().size());
    seatRepository.getEntityManager().clear();
    var updatedSeats = seatIds.stream().map(seatRepository::find).collect(Collectors.toList());
    updatedSeats.forEach(seat -> assertEquals(1, seat.getReservations().size()));
  }
}
