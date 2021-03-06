package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.reservation.ReservationDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.generic.UpdateEntityControl;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.BeanUtils;
import io.github.janmalch.kino.util.Mapper;
import java.util.stream.Collectors;

public class UpdateReservationByIdControl extends ManagingControl<Void> {

  private final ReservationDto reservationDto;
  private final long id;
  private final Role role;
  private final String accountName;
  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);
  private final Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);

  public UpdateReservationByIdControl(
      long id, String accountName, Role role, ReservationDto reservationDto) {
    this.reservationDto = reservationDto;
    this.id = id;
    this.accountName = accountName;
    this.role = role;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Void> result) {
    manage(reservationRepository, seatRepository);
    var reservation =
        Problems.requireEntity(reservationRepository.find(id), id, "No such Reservation");
    var reservationValidator = new ReservationValidator();

    var ownerProblem = reservationValidator.checkOwner(reservation, accountName, role);
    if (ownerProblem.isPresent()) {
      return result.failure(ownerProblem.get());
    }

    var validationProblem = reservationValidator.validate(reservationDto);
    if (validationProblem.isPresent()) {
      return result.failure(validationProblem.get());
    }

    var mapper = new UpdateReservationMapper();
    return new UpdateEntityControl<>(id, reservationDto, Reservation.class, mapper).execute(result);
  }

  class UpdateReservationMapper implements Mapper<ReservationDto, Reservation> {

    @Override
    public Reservation update(ReservationDto update, Reservation existing) {
      var reservation = BeanUtils.clone(existing, Reservation.class);

      var updatedSeats =
          update.getSeatIds().stream().map(seatRepository::find).collect(Collectors.toSet());
      reservation.setSeats(updatedSeats);

      return reservation;
    }
  }
}
