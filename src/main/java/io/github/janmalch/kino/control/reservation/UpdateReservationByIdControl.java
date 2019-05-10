package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.generic.UpdateEntityControl;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.BeanUtils;
import io.github.janmalch.kino.util.Mapper;
import java.util.stream.Collectors;

public class UpdateReservationByIdControl implements Control<Void> {

  private final ReservationDto reservationDto;
  private final long id;

  public UpdateReservationByIdControl(long id, ReservationDto reservationDto) {
    this.reservationDto = reservationDto;
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var reservationValidator = new ReservationValidator();
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
      Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);

      var updatedSeats =
          update.getSeatIds().stream().map(seatRepository::find).collect(Collectors.toSet());
      reservation.setSeats(updatedSeats);

      return reservation;
    }

    @Override
    public Reservation map(ReservationDto source) {
      throw new UnsupportedOperationException();
    }
  }
}
