package io.github.janmalch.kino.entity;

import java.util.List;
import javax.persistence.*;

@Entity
public class CinemaHall {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "cinemaHall")
  private List<Presentation> presentations;

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
