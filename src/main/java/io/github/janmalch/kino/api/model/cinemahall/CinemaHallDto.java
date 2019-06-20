package io.github.janmalch.kino.api.model.cinemahall;

import io.github.janmalch.kino.api.model.seat.SeatDto;
import java.util.List;

public class CinemaHallDto {

  private long id;
  private List<SeatDto> seats;
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<SeatDto> getSeats() {
    return seats;
  }

  public void setSeats(List<SeatDto> seats) {
    this.seats = seats;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
