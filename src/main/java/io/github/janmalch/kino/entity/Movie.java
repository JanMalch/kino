package io.github.janmalch.kino.entity;

import java.util.List;
import javax.persistence.*;

@Entity
public class Movie implements Identifiable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Presentation> presentations;

  private String name;

  @ManyToOne
  @JoinColumn(name = "PRICECATEGORY_ID")
  private PriceCategory priceCategory;

  private float duration;

  private int ageRating;

  private String imageURL;

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PriceCategory getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(PriceCategory priceCategory) {
    this.priceCategory = priceCategory;
  }

  public float getDuration() {
    return duration;
  }

  public void setDuration(float duration) {
    this.duration = duration;
  }

  public int getAgeRating() {
    return ageRating;
  }

  public void setAgeRating(int ageRating) {
    this.ageRating = ageRating;
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
}
