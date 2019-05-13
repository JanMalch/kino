package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class DeleteReservationByIdControl implements Control<SuccessMessage> {

  private final long id;
  private final String accountName;
  private final Role role;

  public DeleteReservationByIdControl(long id, String accountName, Role role) {
    this.id = id;
    this.accountName = accountName;
    this.role = role;
  }

  @Override
  public <T> T execute(ResultBuilder<T, SuccessMessage> result) {
    var reservationRepository = RepositoryFactory.createRepository(Reservation.class);
    var reservation =
        Problems.requireEntity(reservationRepository.find(id), id, "No such reservation found");

    var reservationValidator = new ReservationValidator();
    var problem = reservationValidator.checkOwner(reservation, accountName, role);
    if (problem.isPresent()) {
      return result.failure(problem.get());
    }

    reservationRepository.remove(reservation);
    return result.success("Reservation successfully deleted");
  }
}
