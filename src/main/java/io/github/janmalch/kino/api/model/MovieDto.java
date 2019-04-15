package io.github.janmalch.kino.api.model;

public class MovieDto {

  private String name;
  private String priceCategory;
  private String startDate;
  private String endDate;
  private Float duration;
  private Integer ageRating;

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

  public Float getDuration() {
    return duration;
  }

  public void setDuration(Float duration) {
    this.duration = duration;
  }

  public Integer getAgeRating() {
    return ageRating;
  }

  public void setAgeRating(Integer ageRating) {
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