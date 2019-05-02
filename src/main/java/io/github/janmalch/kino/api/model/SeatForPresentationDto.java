package io.github.janmalch.kino.api.model;

import java.util.Objects;

public class SeatForPresentationDto {

  private long id;
  private String row;
  private int seatNumber;
  private boolean taken;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRow() {
    return row;
  }

  public void setRow(String row) {
    this.row = row;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

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
        + id
        + ", row='"
        + row
        + '\''
        + ", seatNumber="
        + seatNumber
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
