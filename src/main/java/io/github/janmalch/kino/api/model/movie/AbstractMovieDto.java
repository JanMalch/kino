package io.github.janmalch.kino.api.model.movie;

public abstract class AbstractMovieDto {

  protected String name;
  protected Float duration;
  protected Integer ageRating;
  protected String imageURL;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }
}
