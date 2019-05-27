package io.github.janmalch.kino.api.model.presentation;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewPresentationDto {

  @NotNull private Date date;

  @Min(1)
  private long movieId;

  @Min(1)
  private long cinemaHallId;

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public long getCinemaHallId() {
    return cinemaHallId;
  }

  public void setCinemaHallId(long cinemaHallId) {
    this.cinemaHallId = cinemaHallId;
  }

  @Override
  public String toString() {
    return "NewPresentationDto{"
        + "date="
        + date
        + ", movieId="
        + movieId
        + ", cinemaHallId="
        + cinemaHallId
        + '}';
  }
}
