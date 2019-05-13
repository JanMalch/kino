package io.github.janmalch.kino.api.model;

import java.util.List;

public class PresentationWithSeatsDto extends PresentationDto {

  private List<SeatForPresentationDto> seats;

  public List<SeatForPresentationDto> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatForPresentationDto> seats) {
    this.seats = seats;
  }
}
