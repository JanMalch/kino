package io.github.janmalch.kino.control.reservation;

import io.github.janmalch.kino.api.model.ReservationInfoDto;
import io.github.janmalch.kino.api.model.SeatForReservationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Reservation;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.stream.Collectors;

public class GetReservationByIdControl implements Control<ReservationInfoDto> {

  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);
  private final ReflectionMapper<Seat, SeatForReservationDto> seatMapper = new ReflectionMapper<>();
  private final ReflectionMapper<Reservation, ReservationInfoDto> reservationMapper =
      new ReflectionMapper<>();
  protected final long id;

  public GetReservationByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, ReservationInfoDto> result) {
    var reservation = reservationRepository.find(id);
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

    return result.success(reservationDto);
  }
}
