package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
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
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
