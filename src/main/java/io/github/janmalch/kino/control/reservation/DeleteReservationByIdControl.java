package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class DeleteReservationByIdControl implements Control<Void> {

  private long id;

  public DeleteReservationByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var reservation =
        Problems.requireEntity(reservationRepository.find(id), id, "No such reservation found");
    reservationRepository.remove(reservation);
    return result.success("Reservation successfully deleted");
  }
}
