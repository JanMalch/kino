package io.github.janmalch.kino.api.model.movie;

import javax.validation.constraints.DecimalMin;

public class NewMovieDto extends AbstractMovieDto {
  @DecimalMin("1.0")
  private long priceCategoryId;

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  @DecimalMin("1.0")
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
