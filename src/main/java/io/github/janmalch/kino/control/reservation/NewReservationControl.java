package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.api.model.SeatForPresentationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.generic.NewEntityControl;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.*;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

public class NewReservationControl implements Control<Long> {

  private final ReservationDto reservationDto;
  private final String mail;

  public NewReservationControl(String mail, ReservationDto reservationDto) {
    this.reservationDto = reservationDto;
    this.mail = mail;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Long> result) {
    var validationProblem = validate();
    if (validationProblem.isPresent()) {
      return result.failure(validationProblem.get());
    }

    var mapper = new NewReservationMapper();
    return new NewEntityControl<>(reservationDto, Reservation.class, mapper).execute(result);
  }

  Optional<Problem> validate() {
    var seatsForPresentationControl =
        new GetSeatsWithStatusControl(reservationDto.getPresentationId());
    var result = seatsForPresentationControl.execute(new EitherResultBuilder<>());
    if (result.isFailure()) {
      return Optional.of(result.getProblem());
    }

    var availableIds =
        result
            .getSuccess()
            .getData()
            .stream() // Stream mit allen Sitzen für den Saal
            .filter(
                seatForPresentation ->
                    !seatForPresentation.isTaken()) // nur die verfügbaren Sitze auswählen
            .map(SeatForPresentationDto::getId) // ID benötigt zum Vergleich
            .collect(Collectors.toSet());

    // Alle verfügbaren Sitze aus den angeforderten entfernen
    var unavailableSeats = new HashSet<>(reservationDto.getSeatIds());
    unavailableSeats.removeAll(availableIds);
    // Nicht verfügbare Sitze bleiben übrig
    if (unavailableSeats.size() > 0) {
      var problem =
          Problem.builder()
              .type("new-reservation/seats-unavailable")
              .title("Some seats in the reservation are not available")
              .status(Response.Status.BAD_REQUEST)
              .detail(String.format("%d seat(s) unavailable", unavailableSeats.size()))
              .parameter("unavailable_seats", unavailableSeats)
              .instance()
              .build();
      return Optional.of(problem);
    }

    return Optional.empty();
  }

  class NewReservationMapper implements Mapping<ReservationDto, Reservation> {

    @Override
    public Reservation map(
        ReservationDto source, Class<Reservation> targetClass, Map<String, Object> supplies) {
      Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);
      Repository<Presentation> presentationRepository =
          RepositoryFactory.createRepository(Presentation.class);

      Set<Seat> seats =
          source.getSeatIds().stream().map(seatRepository::find).collect(Collectors.toSet());
      Presentation presentation = presentationRepository.find(source.getPresentationId());

      Reservation reservation = new Reservation();
      reservation.setSeats(seats);
      reservation.setPresentation(presentation);
      reservation.setAccount(extractAccount());
      reservation.setReservationDate(new Date());

      return reservation;
    }

    private Account extractAccount() {
      Repository<Account> accountRepository = RepositoryFactory.createRepository(Account.class);
      Specification<Account> accountSpec = new AccountByEmailSpec(mail);
      var optionalAccount = accountRepository.queryFirst(accountSpec);
      return optionalAccount.get();
    }
  }
}
