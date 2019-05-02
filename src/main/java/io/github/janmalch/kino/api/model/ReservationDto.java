package io.github.janmalch.kino.api.model;

import java.util.HashSet;
import java.util.Set;

public class ReservationDto {

  private Set<Long> seatIds = new HashSet<>();
  private long presentationId;

  public long getPresentationId() {
    return presentationId;
  }

  public void setPresentationId(long presentationId) {
    this.presentationId = presentationId;
  }

  public Set<Long> getSeatIds() {
    return seatIds;
  }

  public void setSeatIds(Set<Long> seatIds) {
    this.seatIds = seatIds;
  }
}
