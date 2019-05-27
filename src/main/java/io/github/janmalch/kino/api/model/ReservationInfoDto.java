package io.github.janmalch.kino.api.model;

import io.github.janmalch.kino.api.model.account.SafeAccountInfoDto;
import io.github.janmalch.kino.api.model.movie.MovieInfoDto;
import java.util.Date;
import java.util.List;

public class ReservationInfoDto {

  private List<SeatDto> seats;
  private long id;
  private Date reservationDate;
  private long presentationId;
  private MovieInfoDto movie;
  private SafeAccountInfoDto account;

  public List<SeatDto> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatDto> seats) {
    this.seats = seats;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(Date reservationDate) {
    this.reservationDate = reservationDate;
  }

  public long getPresentationId() {
    return presentationId;
  }

  public MovieInfoDto getMovie() {
    return movie;
  }

  public void setMovie(MovieInfoDto movie) {
    this.movie = movie;
  }

  public void setPresentationId(long presentationId) {
    this.presentationId = presentationId;
  }

  public SafeAccountInfoDto getAccount() {
    return account;
  }

  public void setAccount(SafeAccountInfoDto account) {
    this.account = account;
  }
}
