package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.api.model.SeatForReservationDto;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.util.Mapping;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.Map;
import java.util.stream.Collectors;

class GetReservationMapper implements Mapping<Reservation, ReservationInfoDto> {

  private final ReflectionMapper<Seat, SeatForReservationDto> seatMapper = new ReflectionMapper<>();

  @Override
  public ReservationInfoDto map(
      Reservation reservation,
      Class<ReservationInfoDto> targetClass,
      Map<String, Object> supplies) {
    var seats = reservation.getSeats();
    var mappedSeats =
        seats
            .stream()
            .map(s -> seatMapper.map(s, SeatForReservationDto.class))
            .collect(Collectors.toList());

    ReservationInfoDto reservationDto = new ReservationInfoDto();
    reservationDto.setSeats(mappedSeats);
    reservationDto.setPresentationId(reservation.getPresentation().getId());
    reservationDto.setId(reservation.getId());
    reservationDto.setReservationDate(reservation.getReservationDate());

    return reservationDto;
  }
}
