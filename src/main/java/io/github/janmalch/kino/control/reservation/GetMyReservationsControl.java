package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.reservation.ReservationInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.ReservationsByEmailSpec;
import java.util.List;
import java.util.stream.Collectors;

public class GetMyReservationsControl extends ManagingControl<List<ReservationInfoDto>> {

  private final String myAccountName;
  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);

  public GetMyReservationsControl(String myAccountName) {
    this.myAccountName = myAccountName;
  }

  @Override
  public <T> T compute(ResultBuilder<T, List<ReservationInfoDto>> result) {
    manage(reservationRepository);
    var mapper = new GetReservationMapper();
    var spec = new ReservationsByEmailSpec(myAccountName, reservationRepository);

    var reservations = reservationRepository.query(spec);
    var reservationDtos = reservations.stream().map(mapper::map).collect(Collectors.toList());
    return result.success(reservationDtos);
  }
}
