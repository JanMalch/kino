package io.github.janmalch.kino.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne
  @JoinColumn(name = "PRESENTATION_ID")
  private Presentation presentation;

  private Date reservationDate;

  public Date getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(Date reservationDate) {
    this.reservationDate = reservationDate;
  }
}
