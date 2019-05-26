package io.github.janmalch.kino.api.model.presentation;

import java.util.Date;
import java.util.Objects;

public class PresentationDto {

  private long id;
  private Date date;
  private long cinemaHallId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
    return "PresentationDto{"
        + "id="
        + id
        + ", date="
        + date
        + ", cinemaHallId="
        + cinemaHallId
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PresentationDto that = (PresentationDto) o;
    return getId() == that.getId()
        && getCinemaHallId() == that.getCinemaHallId()
        && Objects.equals(getDate(), that.getDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDate(), getCinemaHallId());
  }
}
