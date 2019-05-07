package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.ReservationByEmailAndIdSpec;

public class UpdateMyReservationControl implements Control<Void> {

  private final String myAccountName;
  private final ReservationDto reservationDto;
  private final long id;

  public UpdateMyReservationControl(String myAccountName, long id, ReservationDto reservationDto) {
    this.myAccountName = myAccountName;
    this.reservationDto = reservationDto;
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var spec = new ReservationByEmailAndIdSpec(myAccountName, id);
    var optionalReservation = reservationRepository.queryFirst(spec);
    var reservation =
        Problems.requireEntity(
            optionalReservation.orElse(null), id, "No such " + Reservation.class.getSimpleName());

    return new UpdateReservationByIdControl(reservation.getId(), reservationDto).execute(result);
  }
}
