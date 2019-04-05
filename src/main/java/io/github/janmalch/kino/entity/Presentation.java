package io.github.janmalch.kino.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Presentation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "presentation")
  private List<Reservation> reservations;

  @ManyToOne
  @JoinColumn(name = "MOVIE_ID")
  private Movie movie;

  @ManyToOne
  @JoinColumn(name = "CINEMAHALL_ID")
  private CinemaHall cinemaHall;

  private Date date;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public CinemaHall getCinemaHall() {
    return cinemaHall;
  }

  public void setCinemaHall(CinemaHall cinemaHall) {
    this.cinemaHall = cinemaHall;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
