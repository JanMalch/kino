package io.github.janmalch.kino.entity;

import java.util.*;
import javax.persistence.*;

@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToMany
  @JoinTable(
      name = "RESERVATION_SEAT",
      joinColumns = @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "ID"),
      inverseJoinColumns = @JoinColumn(name = "SEAT_ID", referencedColumnName = "ID"))
  private Set<Seat> seats = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID")
  private Account account;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Presentation getPresentation() {
    return presentation;
  }

  public void setPresentation(Presentation presentation) {
    this.presentation = presentation;
  }

  /*  public void addSeat(Seat seat){
    this.seats.add(seat);
    seat.getReservations().add(this);
  }

  public void removeSeat(Seat seat){
    this.seats.remove(seat);
    seat.getReservations().remove(this);
  }*/

  public Set<Seat> getSeats() {
    return seats;
  }

  public void setSeats(Set<Seat> seats) {
    /*for (Seat seat: seats) {
      seat.getReservations().add(this);
    }*/
    this.seats = seats;
  }
}
