package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.reservation.ReservationInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class GetReservationByIdControl extends ManagingControl<ReservationInfoDto> {

  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);
  private final long id;
  private final Role role;
  private final String accountName;

  public GetReservationByIdControl(long id, Role role, String accountName) {
    this.id = id;
    this.role = role;
    this.accountName = accountName;
  }

  @Override
  public <T> T compute(ResultBuilder<T, ReservationInfoDto> result) {
    manage(reservationRepository);
    var reservation =
        Problems.requireEntity(reservationRepository.find(id), id, "No such Reservation");

    ReservationValidator reservationValidator = new ReservationValidator();
    var problem = reservationValidator.checkOwner(reservation, accountName, role);

    if (problem.isPresent()) {
      return result.failure(problem.get());
    }

    var mapper = new GetReservationMapper();
    var reservationInfoDto = mapper.map(reservation);

    return result.success(reservationInfoDto);
  }
}
