package io.github.janmalch.kino.entity;

import javax.persistence.*;

@Entity
public class Seat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne
  @JoinColumn(name = "CINEMAHALL_ID")
  private CinemaHall cinemaHall;
}
