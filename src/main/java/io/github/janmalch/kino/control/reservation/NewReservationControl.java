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
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    // more validation needed?
    return validateSeatsAvailable();
  }

  Optional<Problem> validateSeatsAvailable() {
    var seatsForPresentationControl =
        new GetSeatsWithStatusControl(reservationDto.getPresentationId());
    var result = seatsForPresentationControl.execute(new EitherResultBuilder<>());
    if (result.isFailure()) {
      return Optional.of(result.getProblem());
    }

    var requestedSeats = reservationDto.getSeatIds();

    // Überschneidung zwischen den Sitzen der Reservierung und den bereits reservierten Sitzen
    var intersection =
        result
            .getSuccess()
            .getData()
            .stream() // Stream mit allen Sitzen für den Saal
            .filter(SeatForPresentationDto::isTaken) // nur die bereits gebuchten Sitze auswählen
            .map(SeatForPresentationDto::getId) // ID benötigt zum Vergleich
            .filter(requestedSeats::contains) // Sitze rausnehmen, die NICHT bereits gebucht sind
            .collect(Collectors.toSet()); // Schnittmenge bleibt übrig

    if (intersection.size() > 0) {
      var problem =
          Problem.builder()
              .type("new-reservation/seats-taken")
              .title("Seats in reservation are already taken")
              .status(Response.Status.BAD_REQUEST)
              .detail(String.format("%d seat(s) already taken", intersection.size()))
              .parameter("taken_seats", intersection)
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
