package io.github.janmalch.kino.api.model;

import java.util.List;

public class MovieDto {

  private long id;
  private String name;
  private PriceCategoryDto priceCategory;
  private String startDate;
  private String endDate;
  private Float duration;
  private Integer ageRating;
  private List<PresentationDto> presentations;

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

  public List<PresentationDto> getPresentations() {
    return presentations;
  }

  public void setPresentations(List<PresentationDto> presentations) {
    this.presentations = presentations;
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
