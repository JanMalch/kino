package io.github.janmalch.kino.entity;

import java.util.List;
import javax.persistence.*;

@Entity
public class CinemaHall implements Identifiable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "cinemaHall")
  private List<Presentation> presentations;

  @OneToMany(mappedBy = "cinemaHall")
  private List<Seat> seats;

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Presentation> getPresentations() {
    return presentations;
  }

  public void setPresentations(List<Presentation> presentations) {
    this.presentations = presentations;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }
}
