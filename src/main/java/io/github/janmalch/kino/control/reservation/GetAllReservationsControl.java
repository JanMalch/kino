package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllReservationsControl implements Control<List<ReservationInfoDto>> {

  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);

  @Override
  public <T> T execute(ResultBuilder<T, List<ReservationInfoDto>> result) {
    var reservations = reservationRepository.findAll();
    var mapper = new GetReservationMapper();
    var reservationDtos =
        reservations
            .stream()
            .map(r -> mapper.map(r, ReservationInfoDto.class))
            .collect(Collectors.toList());

    return result.success(reservationDtos);
  }
}
