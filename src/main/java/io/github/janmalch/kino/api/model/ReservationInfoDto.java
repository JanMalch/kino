package io.github.janmalch.kino.api.model;

import java.util.Date;
import java.util.List;

public class ReservationInfoDto {

  private List<SeatForReservationDto> seats;
  private long id;
  private Date reservationDate;
  private long presentationId;

  public List<SeatForReservationDto> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatForReservationDto> seats) {
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

  public void setPresentationId(long presentationId) {
    this.presentationId = presentationId;
  }
}
