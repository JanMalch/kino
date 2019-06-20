package io.github.janmalch.kino.api.model.movie;

import java.util.Objects;

public class MovieInfoDto extends AbstractMovieDto {

  private long id;
  private long priceCategoryId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPriceCategoryId() {
    return priceCategoryId;
  }

  public void setPriceCategoryId(long priceCategoryId) {
    this.priceCategoryId = priceCategoryId;
  }

  @Override
  public String toString() {
    return "MovieInfoDto{"
        + "name='"
        + name
        + '\''
        + ", priceCategoryId="
        + priceCategoryId
        + '\''
        + ", duration="
        + duration
        + ", ageRating="
        + ageRating
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovieInfoDto that = (MovieInfoDto) o;
    return priceCategoryId == that.priceCategoryId
        && Float.compare(that.duration, duration) == 0
        && Objects.equals(ageRating, that.ageRating)
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, priceCategoryId, duration, ageRating);
  }
}
