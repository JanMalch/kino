package io.github.janmalch.kino.api.model;

import io.github.janmalch.kino.entity.PriceCategory;
import java.util.List;
import java.util.Objects;

public class MovieDto {

  private String name;
  private PriceCategory priceCategory;
  private String startDate;
  private String endDate;
  private Float duration;
  private Integer ageRating;
  private List<PresentationDto> presentations;

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
        + ", priceCategory="
        + priceCategory
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
        + ", presentations="
        + presentations
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovieDto movieDto = (MovieDto) o;
    return Objects.equals(getName(), movieDto.getName())
        && Objects.equals(getPriceCategory(), movieDto.getPriceCategory())
        && Objects.equals(getStartDate(), movieDto.getStartDate())
        && Objects.equals(getEndDate(), movieDto.getEndDate())
        && Objects.equals(getDuration(), movieDto.getDuration())
        && Objects.equals(getAgeRating(), movieDto.getAgeRating())
        && Objects.equals(getPresentations(), movieDto.getPresentations());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getName(),
        getPriceCategory(),
        getStartDate(),
        getEndDate(),
        getDuration(),
        getAgeRating(),
        getPresentations());
  }
}
