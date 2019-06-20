package io.github.janmalch.kino.api.model.presentation;

import io.github.janmalch.kino.api.model.movie.MovieInfoDto;
import io.github.janmalch.kino.api.model.seat.SeatForPresentationDto;
import java.util.List;

public class PresentationWithSeatsDto extends PresentationDto {

  private List<SeatForPresentationDto> seats;
  private MovieInfoDto movie;

  public List<SeatForPresentationDto> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatForPresentationDto> seats) {
    this.seats = seats;
  }

  public MovieInfoDto getMovie() {
    return movie;
  }

  public void setMovie(MovieInfoDto movie) {
    this.movie = movie;
  }
}
