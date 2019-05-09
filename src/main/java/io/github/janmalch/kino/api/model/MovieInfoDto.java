package io.github.janmalch.kino.api.model;

import java.util.Date;
import java.util.Objects;

// TODO: improve redundancy
public class MovieInfoDto {

  private long id;
  private String name;
  private long priceCategoryId;
  private Date startDate;
  private Date endDate;
  private float duration;
  private int ageRating;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
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

  @Override
  public String toString() {
    return "MovieInfoDto{"
        + "name='"
        + name
        + '\''
        + ", priceCategoryId="
        + priceCategoryId
        + ", startDate='"
        + startDate
        + '\''
        + ", endDate='"
        + endDate
        + '\''
        + ", duration="
        + duration
        + ", ageRating="
        + ageRating
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovieInfoDto that = (MovieInfoDto) o;
    return priceCategoryId == that.priceCategoryId
        && Float.compare(that.duration, duration) == 0
        && ageRating == that.ageRating
        && Objects.equals(name, that.name)
        && Objects.equals(startDate, that.startDate)
        && Objects.equals(endDate, that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, priceCategoryId, startDate, endDate, duration, ageRating);
  }
}
