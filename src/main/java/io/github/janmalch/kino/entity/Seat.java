package io.github.janmalch.kino.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Seat implements Identifiable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToMany(mappedBy = "seats")
  private Set<Reservation> reservations = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "CINEMAHALL_ID")
  private CinemaHall cinemaHall;

  private String row;

  private int seatNumber;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public CinemaHall getCinemaHall() {
    return cinemaHall;
  }

  public void setCinemaHall(CinemaHall cinemaHall) {
    this.cinemaHall = cinemaHall;
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

  public Set<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(Set<Reservation> reservations) {
    this.reservations = reservations;
  }

  @Override
  public String toString() {
    return "Seat{" + "id=" + id + ", row='" + row + '\'' + ", seatNumber=" + seatNumber + '}';
  }
}
