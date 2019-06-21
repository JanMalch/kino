package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.SeatForPresentationDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Returns a list of seats for the given presentation.
 * The list includes all seats of the related Cinema Hall.
 * Additionally it includes a status property, indicating if the seat is taken for the specified presentation
 */
public class GetSeatsWithStatusControl extends ManagingControl<List<SeatForPresentationDto>> {

  private final long presentationId;
  private final ReflectionMapper<Seat, SeatForPresentationDto> mapper =
      new ReflectionMapper<>(SeatForPresentationDto.class);
  private final Repository<Presentation> repository =
      RepositoryFactory.createRepository(Presentation.class);

  public GetSeatsWithStatusControl(long presentationId) {
    this.presentationId = presentationId;
  }

  @Override
  public <T> T compute(ResultBuilder<T, List<SeatForPresentationDto>> result) {
    manage(repository);
    var presentation =
        Problems.requireEntity(
            repository.find(presentationId), presentationId, "No such presentation");

    var allSeats = presentation.getCinemaHall().getSeats();
    var reservedSeatIds =
        presentation
            .getReservations()
            .stream()
            .flatMap(reservation -> reservation.getSeats().stream())
            .map(Seat::getId)
            .collect(Collectors.toSet());

    var mappedSeats =
        allSeats
            .stream()
            .map(
                seat -> {
                  var data = mapper.map(seat);
                  var isTaken = reservedSeatIds.contains(seat.getId());
                  data.setTaken(isTaken);
                  return data;
                })
            .collect(Collectors.toList());

    return result.success(mappedSeats);
  }
}
