package io.github.janmalch.kino.control.presentation;

import io.github.janmalch.kino.api.model.PresentationWithSeatsDto;
import io.github.janmalch.kino.control.reservation.GetSeatsWithStatusControl;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.either.EitherResultBuilder;

public class PresentationWithSeatsDtoMapper
    implements Mapper<Presentation, PresentationWithSeatsDto> {
  @Override
  public PresentationWithSeatsDto map(Presentation source) {
    var dto = new PresentationWithSeatsDto();
    dto.setId(source.getId());
    dto.setCinemaHallId(source.getCinemaHall().getId());
    dto.setDate(source.getDate());

    var control = new GetSeatsWithStatusControl(source.getId());
    var seats = control.execute(new EitherResultBuilder<>()).getSuccess();
    dto.setSeats(seats);

    return dto;
  }
}