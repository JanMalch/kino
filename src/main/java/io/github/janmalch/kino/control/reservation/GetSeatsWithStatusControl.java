package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.SeatForPresentationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.List;
import java.util.stream.Collectors;

public class GetSeatsWithStatusControl implements Control<List<SeatForPresentationDto>> {

  private final long presentationId;
  private final ReflectionMapper<Seat, SeatForPresentationDto> mapper = new ReflectionMapper<>();
  private final Repository<Presentation> repository =
      RepositoryFactory.createRepository(Presentation.class);

  public GetSeatsWithStatusControl(long presentationId) {
    this.presentationId = presentationId;
  }

  @Override
  public <T> T execute(ResultBuilder<T, List<SeatForPresentationDto>> result) {
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
                  var data = mapper.map(seat, SeatForPresentationDto.class);
                  var isTaken = reservedSeatIds.contains(seat.getId());
                  data.setTaken(isTaken);
                  return data;
                })
            .collect(Collectors.toList());

    return result.success(mappedSeats);
  }
}
