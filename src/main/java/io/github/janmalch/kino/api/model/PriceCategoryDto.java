package io.github.janmalch.kino.api.model;

import java.util.Objects;

public class PriceCategoryDto extends PriceCategoryBaseDto {

  private long id;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "PriceCategoryDto{"
        + "id="
        + id
        + ", name='"
        + getName()
        + '\''
        + ", regularPrice="
        + getRegularPrice()
        + ", reducedPrice="
        + getReducedPrice()
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PriceCategoryDto that = (PriceCategoryDto) o;
    return getId() == that.getId()
        && Float.compare(that.getRegularPrice(), getRegularPrice()) == 0
        && Float.compare(that.getReducedPrice(), getReducedPrice()) == 0
        && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getRegularPrice(), getReducedPrice());
  }
}
