package io.github.janmalch.kino.api.model;

public class MovieDto {

  private String name;
  private String priceCategory;
  private String startDate;
  private String endDate;
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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
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
    return "MovieDto{"
        + "name='"
        + name
        + '\''
        + ", priceCategory='"
        + priceCategory
        + '\''
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", duration="
        + duration
        + ", ageRating="
        + ageRating
        + '}';
  }
}
