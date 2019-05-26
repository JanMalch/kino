package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.generic.NewEntityControl;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.util.Mapper;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class NewReservationControl extends ManagingControl<Long> {

  private final ReservationDto reservationDto;
  private final String mail;
  private final Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);
  private final Repository<Account> accountRepository =
      RepositoryFactory.createRepository(Account.class);
  private final Repository<Presentation> presentationRepository =
      RepositoryFactory.createRepository(Presentation.class);

  public NewReservationControl(String mail, ReservationDto reservationDto) {
    this.reservationDto = reservationDto;
    this.mail = mail;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Long> result) {
    manage(seatRepository, accountRepository, presentationRepository);
    var reservationValidator = new ReservationValidator();
    var validationProblem = reservationValidator.validate(reservationDto);
    if (validationProblem.isPresent()) {
      return result.failure(validationProblem.get());
    }

    var mapper = new NewReservationMapper();
    return new NewEntityControl<>(reservationDto, Reservation.class, mapper).execute(result);
  }

  class NewReservationMapper implements Mapper<ReservationDto, Reservation> {

    @Override
    public Reservation map(ReservationDto source) {
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
      Specification<Account> accountSpec = new AccountByEmailSpec(mail, accountRepository);
      var optionalAccount = accountRepository.queryFirst(accountSpec);
      return optionalAccount.get();
    }
  }
}
