package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import javax.ws.rs.core.Response;

public class GetReservationByIdControl implements Control<ReservationInfoDto> {

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
  public <T> T execute(ResultBuilder<T, ReservationInfoDto> result) {

    var reservation =
        Problems.requireEntity(reservationRepository.find(id), id, "No such Reservation");
    var isOwner = accountName.equals(reservation.getAccount().getEmail());

    if (role == Role.CUSTOMER && !isOwner) {
      var problem =
          Problem.builder()
              .type("no-such-entity")
              .title("Failed to find entity for given ID")
              .detail("No such reservation")
              .status(Response.Status.NOT_FOUND)
              .instance()
              .parameter("id", id)
              .build();
      return result.failure(problem);
    }

    var mapper = new GetReservationMapper();
    var reservationInfoDto = mapper.map(reservation);

    return result.success(reservationInfoDto);
  }
}
