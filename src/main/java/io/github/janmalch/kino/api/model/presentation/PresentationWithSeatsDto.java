package io.github.janmalch.kino.api.model.presentation;

import io.github.janmalch.kino.api.model.MovieInfoDto;
import io.github.janmalch.kino.api.model.SeatForPresentationDto;
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
