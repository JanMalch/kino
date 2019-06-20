package io.github.janmalch.kino.control.cinemahall;

import io.github.janmalch.kino.api.model.cinemahall.CinemaHallDto;
import io.github.janmalch.kino.api.model.seat.SeatDto;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.stream.Collectors;

public class CinemaHallDtoMapper implements Mapper<CinemaHall, CinemaHallDto> {
  @Override
  public CinemaHallDto map(CinemaHall source) {
    var seatMapper = new ReflectionMapper<Seat, SeatDto>(SeatDto.class);
    var dto = new CinemaHallDto();
    dto.setId(source.getId());
    dto.setName(source.getName());
    dto.setSeats(source.getSeats().stream().map(seatMapper::map).collect(Collectors.toList()));
    return dto;
  }
}
