package io.github.janmalch.kino.api.model.movie;

public class NewMovieDto {

  private String name;
  private long priceCategoryId;
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

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
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
    return "NewMovieDto{"
        + "name='"
        + name
        + '\''
        + ", priceCategoryId="
        + priceCategoryId
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
