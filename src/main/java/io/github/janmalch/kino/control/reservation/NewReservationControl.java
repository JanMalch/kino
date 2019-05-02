package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.util.Mapper;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class NewReservationControl implements Control<Long> {

  private final ReservationDto reservationDto;
  private final Repository<Reservation> repository;
  private final Token token;

  public NewReservationControl(Token token, ReservationDto reservationDto) {
    this.reservationDto = reservationDto;
    this.repository = RepositoryFactory.createRepository(Reservation.class);
    this.token = token;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Long> result) {
    var validationProblem = validate();
    if (validationProblem.isPresent()) {
      return result.failure(validationProblem.get());
    }
    var mapper = new NewReservationMapper();
    var entity = mapper.mapToEntity(reservationDto);

    repository.add(entity);
    return result.success(entity.getId(), "Created new reservation");
  }

  Optional<Problem> validate() {

    return Optional.empty();
  }

  class NewReservationMapper implements Mapper<Reservation, ReservationDto> {

    @Override
    public Reservation mapToEntity(ReservationDto domain) {
      var reservation = new Reservation();

      reservation.setReservationDate(new Date());
      reservation.setAccount(extractAccount());
      reservation.setSeats(extractSeats(domain.getSeatIds()));
      reservation.setPresentation(extractPresentation(domain.getPresentationId()));
      return reservation;
    }

    private Set<Seat> extractSeats(Set<Long> seatIds) {
      Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);
      Seat seat;
      Set<Seat> seats = new HashSet<>();

      for (Long id : seatIds) {
        seat = seatRepository.find(id);
        seats.add(seat);
      }
      return seats;
    }

    private Presentation extractPresentation(Long presentationId) {
      Repository<Presentation> presentationRepository =
          RepositoryFactory.createRepository(Presentation.class);
      return presentationRepository.find(presentationId);
    }

    private Account extractAccount() {
      Repository<Account> accountRepository = RepositoryFactory.createRepository(Account.class);
      Specification<Account> accountSpec = new UserByEmailSpec(token.getName());
      var optionalAccount = accountRepository.queryFirst(accountSpec);
      return optionalAccount.get();
    }
  }
}
