package io.github.janmalch.kino.api.model.movie;

public class NewMovieDto {

  private String name;
  private long priceCategoryId;
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

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
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
        + ", duration="
        + duration
        + ", ageRating="
        + ageRating
        + '}';
  }
}
