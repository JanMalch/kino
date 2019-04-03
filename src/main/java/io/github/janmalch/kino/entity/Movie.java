package io.github.janmalch.kino.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "movie")
  private List<Presentation> presentations;

  private String name;

  private String priceCategory;

  private Date startDate;

  private Date endDate;

  private float duration;

  private int ageRating;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(String priceCategory) {
    this.priceCategory = priceCategory;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
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
}
