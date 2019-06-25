package io.github.janmalch.kino.control.cinemahall;

import io.github.janmalch.kino.api.model.cinemahall.NewCinemaHallDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Seat;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adds a new Cinema Hall based on the input dto. The seat names are calculated based on the given
 * dimensions. After adding the cinema hall the seats will be added. If any error occured the cinema
 * hall will be deleted.
 */
public class NewCinemaHallControl extends ManagingControl<Long> {

  private final Logger logger = LoggerFactory.getLogger(NewCinemaHallControl.class);
  private final NewCinemaHallDto dto;
  private final Repository<CinemaHall> cinemaHallRepository =
      RepositoryFactory.createRepository(CinemaHall.class);
  private final Repository<Seat> seatRepository = RepositoryFactory.createRepository(Seat.class);

  public NewCinemaHallControl(NewCinemaHallDto dto) {
    this.dto = dto;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Long> result) {
    manage(cinemaHallRepository, seatRepository);
    // contents have been validated by boundary
    var entity = new CinemaHall();
    entity.setName(dto.getName());
    cinemaHallRepository.add(entity);

    var seats =
        IntStream.range(0, dto.getRowCount())
            .mapToObj(number -> (char) ('A' + number))
            .flatMap(
                row ->
                    IntStream.rangeClosed(1, dto.getSeatsPerRow())
                        .mapToObj(seatNumInRow -> Pair.of(row, seatNumInRow)))
            .map(
                seatName -> {
                  var seatEntity = new Seat();
                  seatEntity.setSeatNumber(seatName.getRight());
                  seatEntity.setRow(seatName.getLeft().toString());
                  seatEntity.setCinemaHall(entity);
                  return seatEntity;
                })
            .collect(Collectors.toList());

    try {
      seatRepository.add(seats);
    } catch (Exception e) {
      // Das Erstellen der Hall findet in zwei Schritten statt
      // Da die Hall bereits persistiert wurde, muss auf alle Fälle das persistieren der Sitze
      //  fehlerfrei durchlaufen. Daher werden hier alle Fehler gefangen und ggf. zurückgerollt.
      logger.error("Error while persisting seats. Roll back cinema hall creation", e);
      cinemaHallRepository.remove(entity);
      var problem =
          Problem.builder(Response.Status.INTERNAL_SERVER_ERROR)
              .instance()
              .detail("Unknown error while saving new cinema hall with seats")
              .build();
      return result.failure(problem);
    }

    return result.success(entity.getId());
  }
}
