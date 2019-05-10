package io.github.janmalch.kino.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Reservation implements Identifiable {
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

  public Set<Seat> getSeats() {
    return seats;
  }

  public void setSeats(Set<Seat> seats) {
    this.seats = seats;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Reservation)) return false;
    Reservation that = (Reservation) o;
    return id == that.id
        && seats.equals(that.seats)
        && account.equals(that.account)
        && presentation.equals(that.presentation)
        && reservationDate.equals(that.reservationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, seats, account, presentation, reservationDate);
  }
}
