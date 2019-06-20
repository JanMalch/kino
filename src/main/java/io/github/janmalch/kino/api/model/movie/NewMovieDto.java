package io.github.janmalch.kino.api.model.movie;

public class NewMovieDto extends AbstractMovieDto {

  private long priceCategoryId;

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

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
