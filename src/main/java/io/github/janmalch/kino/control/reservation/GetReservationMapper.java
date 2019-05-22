package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.MovieInfoDto;
import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.api.model.SeatDto;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.util.Mapper;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.stream.Collectors;

class GetReservationMapper implements Mapper<Reservation, ReservationInfoDto> {

  private final ReflectionMapper<Seat, SeatDto> seatMapper = new ReflectionMapper<>(SeatDto.class);
  private final ReflectionMapper<Movie, MovieInfoDto> movieMapper =
      new ReflectionMapper<>(MovieInfoDto.class);

  @Override
  public ReservationInfoDto map(Reservation reservation) {
    var seats = reservation.getSeats();
    var mappedSeats = seats.stream().map(seatMapper::map).collect(Collectors.toList());

    ReservationInfoDto reservationDto = new ReservationInfoDto();
    reservationDto.setSeats(mappedSeats);
    reservationDto.setPresentationId(reservation.getPresentation().getId());
    reservationDto.setId(reservation.getId());
    reservationDto.setReservationDate(reservation.getReservationDate());
    reservationDto.setMovie(movieMapper.map(reservation.getPresentation().getMovie()));

    return reservationDto;
  }
}
