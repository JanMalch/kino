package io.github.janmalch.kino.entity;

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
}
