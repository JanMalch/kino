package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.ReservationByEmailAndIdSpec;

public class DeleteMyReservationByIdControl implements Control<SuccessMessage> {

  private long id;
  private String accountName;

  public DeleteMyReservationByIdControl(long id, String accountName) {
    this.id = id;
    this.accountName = accountName;
  }

  @Override
  public <T> T execute(ResultBuilder<T, SuccessMessage> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var spec = new ReservationByEmailAndIdSpec(accountName, id);
    var reservation =
        Problems.requireEntity(
            reservationRepository.queryFirst(spec).orElse(null), id, "No such reservation");
    // reservationRepository.remove(reservation); - does not work

    return new DeleteReservationByIdControl(reservation.getId()).execute(result);
    // return result.success(null, "Reservation successfully deleted");
  }
}
