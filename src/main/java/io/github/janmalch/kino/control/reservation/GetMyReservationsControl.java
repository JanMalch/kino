package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.ReservationsByEmailSpec;
import java.util.List;
import java.util.stream.Collectors;

public class GetMyReservationsControl implements Control<List<ReservationInfoDto>> {

  private final String myAccountName;

  public GetMyReservationsControl(String myAccountName) {
    this.myAccountName = myAccountName;
  }

  @Override
  public <T> T execute(ResultBuilder<T, List<ReservationInfoDto>> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var spec = new ReservationsByEmailSpec(myAccountName);

    var reservations = reservationRepository.query(spec);
    var reservationDtos =
        reservations
            .stream()
            .map(r -> new GetReservationMapper().map(r, ReservationInfoDto.class))
            .collect(Collectors.toList());
    return result.success(reservationDtos);
  }
}
