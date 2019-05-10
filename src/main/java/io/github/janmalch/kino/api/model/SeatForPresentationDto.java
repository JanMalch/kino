package io.github.janmalch.kino.api.model;

import java.util.Objects;

public class SeatForPresentationDto extends SeatForReservationDto {

  private boolean taken;

  public boolean isTaken() {
    return taken;
  }

  public void setTaken(boolean taken) {
    this.taken = taken;
  }

  @Override
  public String toString() {
    return "SeatForPresentationDto{"
        + "id="
        + getId()
        + ", row='"
        + getRow()
        + '\''
        + ", seatNumber="
        + getSeatNumber()
        + ", taken="
        + taken
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SeatForPresentationDto that = (SeatForPresentationDto) o;
    return getId() == that.getId()
        && getSeatNumber() == that.getSeatNumber()
        && isTaken() == that.isTaken()
        && Objects.equals(getRow(), that.getRow());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getRow(), getSeatNumber(), isTaken());
  }
}
