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
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllReservationsControl implements Control<List<ReservationInfoDto>> {

  private final ReflectionMapper<Seat, SeatForReservationDto> seatMapper = new ReflectionMapper<>();
  private final ReflectionMapper<Reservation, ReservationInfoDto> reservationMapper =
      new ReflectionMapper<>();
  private final Repository<Reservation> reservationRepository =
      RepositoryFactory.createRepository(Reservation.class);

  @Override
  public <T> T execute(ResultBuilder<T, List<ReservationInfoDto>> result) {
    var reservations = reservationRepository.findAll();
    var reservationDtos =
        reservations
            .stream()
            .map(
                r -> {
                  var control = new GetReservationByIdControl(r.getId());
                  var optional = control.execute(new EitherResultBuilder<>());
                  return optional.getSuccess().getData();
                })
            .collect(Collectors.toList());

    return result.success(reservationDtos);
    /*        var reservationDtoList = reservations.stream().map(r ->
    {
        var reservationDto = reservationMapper.map(r, ReservationInfoDto.class);
        reservationDto.setPresentationId(r.getPresentation().getId());
        var seatDto = r.getSeats().stream()
                .map(s -> seatMapper.map(s, SeatForReservationDto.class))
                .collect(Collectors.toList());
        reservationDto.setSeats(seatDto);
        return reservationDto;

    }).collect(Collectors.toList());
    return result.success(reservationDtoList);*/
  }
}
