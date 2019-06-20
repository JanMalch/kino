package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.reservation.ReservationDto;
import io.github.janmalch.kino.api.model.seat.SeatForPresentationDto;
import io.github.janmalch.kino.control.validation.Validator;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.core.Response;

public class ReservationValidator implements Validator<ReservationDto> {

  public Optional<Problem> checkOwner(Reservation reservation, String accountName, Role role) {
    var isOwner = accountName.equals(reservation.getAccount().getEmail());
    if (role == Role.CUSTOMER && !isOwner) {
      var problem = Problems.Factory.noSuchEntity(reservation.getId(), "No such reservation");
      return Optional.of(problem);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Problem> validate(ReservationDto reservationDto) {
    var seatsForPresentationControl =
        new GetSeatsWithStatusControl(reservationDto.getPresentationId());
    var result = seatsForPresentationControl.execute(new EitherResultBuilder<>());

    var availableIds =
        result
            .getSuccess()
            .stream() // Stream includes all seats for cinemaHall
            .filter(
                seatForPresentation ->
                    !seatForPresentation.isTaken()) // choose only available seats
            .map(SeatForPresentationDto::getId) // ID needed for comparison
            .collect(Collectors.toSet());

    // remove all available seats from requested ones
    var unavailableSeats = new HashSet<>(reservationDto.getSeatIds());
    unavailableSeats.removeAll(availableIds);
    // non available seats remain
    if (unavailableSeats.size() > 0) {
      var problem =
          Problem.builder()
              .type("new-reservation/seats-unavailable")
              .title("Some seats in the reservation are not available")
              .status(Response.Status.BAD_REQUEST)
              .detail(String.format("%d seat(s) unavailable", unavailableSeats.size()))
              .parameter("unavailable_seats", unavailableSeats)
              .instance()
              .build();
      return Optional.of(problem);
    }
    return Optional.empty();
  }
}
