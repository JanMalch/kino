package io.github.janmalch.kino.api.model.cinemahall;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCinemaHallDto {

  @NotNull
  @Size(min = 1)
  private String name;

  @Min(1)
  private int rowCount;

  @Min(1)
  private int seatsPerRow;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRowCount() {
    return rowCount;
  }

  public void setRowCount(int rowCount) {
    this.rowCount = rowCount;
  }

  public int getSeatsPerRow() {
    return seatsPerRow;
  }

  public void setSeatsPerRow(int seatsPerRow) {
    this.seatsPerRow = seatsPerRow;
  }

  @Override
  public String toString() {
    return "NewCinemaHallDto{"
        + "name='"
        + name
        + "'"
        + ", rowCount="
        + rowCount
        + ", seatsPerRow="
        + seatsPerRow
        + '}';
  }
}
