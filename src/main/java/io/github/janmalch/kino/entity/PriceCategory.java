package io.github.janmalch.kino.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class PriceCategory implements Identifiable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "priceCategory")
  private List<Movie> movies;

  private String name;

  private float regularPrice;

  private float reducedPrice;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PriceCategory)) return false;
    PriceCategory that = (PriceCategory) o;
    return Float.compare(that.getRegularPrice(), getRegularPrice()) == 0
        && Float.compare(that.getReducedPrice(), getReducedPrice()) == 0
        && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getRegularPrice(), getReducedPrice());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getRegularPrice() {
    return regularPrice;
  }

  public void setRegularPrice(float regularPrice) {
    this.regularPrice = regularPrice;
  }

  public float getReducedPrice() {
    return reducedPrice;
  }

  public void setReducedPrice(float reducedPrice) {
    this.reducedPrice = reducedPrice;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Movie> getMovies() {
    return movies;
  }

  public void setMovies(List<Movie> movies) {
    this.movies = movies;
  }
}
