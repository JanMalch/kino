package io.github.janmalch.kino.control.presentation;

import io.github.janmalch.kino.api.model.presentation.PresentationDto;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.util.Mapper;

public class PresentationDtoMapper implements Mapper<Presentation, PresentationDto> {
  @Override
  public PresentationDto map(Presentation source) {
    var dto = new PresentationDto();
    dto.setId(source.getId());
    dto.setCinemaHallId(source.getCinemaHall().getId());
    dto.setDate(source.getDate());
    return dto;
  }
}
