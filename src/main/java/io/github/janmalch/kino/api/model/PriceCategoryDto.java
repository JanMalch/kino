package io.github.janmalch.kino.api.model;

import java.util.Objects;

public class PriceCategoryDto {

  private long id;
  private String name;
  private float regularPrice;
  private float reducedPrice;

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

  public float getRegularPrice() {
    return regularPrice;
  }

  public void setRegularPrice(float regularPrice) {
    this.regularPrice = regularPrice;
  }

  public float getReducedPrice() {
    return reducedPrice;
  }

  public void setReducedPrice(float reducedPrice) {
    this.reducedPrice = reducedPrice;
  }

  @Override
  public String toString() {
    return "PriceCategoryDto{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", regularPrice="
        + regularPrice
        + ", reducedPrice="
        + reducedPrice
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
