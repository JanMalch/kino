package io.github.janmalch.kino.api.model.movie;

import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.api.model.presentation.PresentationDto;
import java.util.List;

public class MovieDto {

  private long id;
  private String name;
  private PriceCategoryDto priceCategory;
  private Float duration;
  private Integer ageRating;
  private List<PresentationDto> presentations;
  private String imageURL;

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

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

  public PriceCategoryDto getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(PriceCategoryDto priceCategory) {
    this.priceCategory = priceCategory;
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

  public List<PresentationDto> getPresentations() {
    return presentations;
  }

  public void setPresentations(List<PresentationDto> presentations) {
    this.presentations = presentations;
  }

  @Override
  public String toString() {
    return "MovieDto{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", priceCategory="
        + priceCategory
        + ", duration="
        + duration
        + ", ageRating="
        + ageRating
        + ", presentations="
        + presentations
        + ", imageURL='"
        + imageURL
        + '\''
        + '}';
  }
}
