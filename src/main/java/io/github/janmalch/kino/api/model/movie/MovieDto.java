package io.github.janmalch.kino.api.model.movie;

import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.api.model.presentation.PresentationDto;
import java.util.List;

public class MovieDto extends AbstractMovieDto {

  private long id;
  private PriceCategoryDto priceCategory;
  private List<PresentationDto> presentations;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PriceCategoryDto getPriceCategory() {
    return priceCategory;
  }

  public void setPriceCategory(PriceCategoryDto priceCategory) {
    this.priceCategory = priceCategory;
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
