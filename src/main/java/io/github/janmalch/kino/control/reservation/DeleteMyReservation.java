package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMyReservation extends ManagingControl<SuccessMessage> {

  private Logger log = LoggerFactory.getLogger(DeleteMyReservation.class);
  ReservationInfoDto myReservationInfoDto;
  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);

  public DeleteMyReservation(ReservationInfoDto myReservationInfoDto) {
    this.myReservationInfoDto = myReservationInfoDto;
  }

  @Override
  public <T> T compute(ResultBuilder<T, SuccessMessage> result) {
    manage(reservationRepository);
    log.info("Deleting user reservation id: " + myReservationInfoDto.getId());
    var reservation =
        Problems.requireEntity(
            reservationRepository.find(myReservationInfoDto.getId()),
            myReservationInfoDto.getId(),
            "No such reservation found");

    if (!reservation.getAccount().getEmail().equals(myReservationInfoDto.getAccount().getEmail())) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot delete my Reservation")
              .instance()
              .build());
    }

    reservationRepository.remove(reservation);
    return result.success("Reservation successfully deleted");
  }
}
