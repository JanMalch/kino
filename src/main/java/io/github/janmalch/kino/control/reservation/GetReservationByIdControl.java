package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class GetReservationByIdControl implements Control<ReservationInfoDto> {

  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);
  protected final long id;

  public GetReservationByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, ReservationInfoDto> result) {
    var reservation = reservationRepository.find(id);
    var mapper = new GetReservationMapper();
    var reservationInfoDto = mapper.map(reservation, ReservationInfoDto.class);

    return result.success(reservationInfoDto);
  }
}
