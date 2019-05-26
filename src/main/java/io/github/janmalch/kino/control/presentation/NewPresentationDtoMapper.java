package io.github.janmalch.kino.control.presentation;

import io.github.janmalch.kino.api.model.presentation.NewPresentationDto;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;

public class NewPresentationDtoMapper implements Mapper<NewPresentationDto, Presentation> {

  private final Repository<Movie> movieRepository = RepositoryFactory.createRepository(Movie.class);
  private final Repository<CinemaHall> cinemaHallRepository =
      RepositoryFactory.createRepository(CinemaHall.class);

  @Override
  public Presentation map(NewPresentationDto source) {
    var entity = new Presentation();
    entity.setDate(source.getDate());
    entity.setMovie(movieRepository.find(source.getMovieId()));
    entity.setCinemaHall(cinemaHallRepository.find(source.getCinemaHallId()));
    return entity;
  }

  @Override
  public Presentation update(NewPresentationDto update, Presentation existing) {
    if (update.getDate() != null) {
      existing.setDate(update.getDate());
    }
    if (update.getMovieId() > 0) {
      existing.setMovie(movieRepository.find(update.getMovieId()));
    }
    if (update.getCinemaHallId() > 0) {
      existing.setCinemaHall(cinemaHallRepository.find(update.getCinemaHallId()));
    }
    return existing;
  }
}
