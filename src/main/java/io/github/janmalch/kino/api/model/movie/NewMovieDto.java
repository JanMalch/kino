package io.github.janmalch.kino.api.model.movie;

import javax.validation.constraints.Min;

public class NewMovieDto extends AbstractMovieDto {
  @Min(1)
  private long priceCategoryId;

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  @Min(1)
  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
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
