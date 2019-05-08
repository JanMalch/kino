package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.ReservationByEmailAndIdSpec;

public class GetMyReservationByIdControl implements Control<ReservationInfoDto> {

  private final String myAccountName;
  private final long id;

  public GetMyReservationByIdControl(long id, String myAccountName) {
    this.id = id;
    this.myAccountName = myAccountName;
  }

  @Override
  public <T> T execute(ResultBuilder<T, ReservationInfoDto> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var spec = new ReservationByEmailAndIdSpec(myAccountName, id);
    var optionalReservation = reservationRepository.queryFirst(spec);
    var reservation =
        Problems.requireEntity(
            optionalReservation.orElse(null), id, "No such " + Reservation.class.getSimpleName());

    return new GetReservationByIdControl(reservation.getId()).execute(result);
  }
}
