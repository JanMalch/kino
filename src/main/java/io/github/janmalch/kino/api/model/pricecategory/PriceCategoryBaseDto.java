package io.github.janmalch.kino.api.model.pricecategory;

import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PriceCategoryBaseDto {
  @NotNull
  @Size(min = 1)
  private String name;

  @DecimalMin("1.0")
  private float regularPrice;

  @DecimalMin("1.0")
  private float reducedPrice;

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
    return "PriceCategoryBaseDto{"
        + "name='"
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
    PriceCategoryBaseDto that = (PriceCategoryBaseDto) o;
    return Float.compare(that.regularPrice, regularPrice) == 0
        && Float.compare(that.reducedPrice, reducedPrice) == 0
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, regularPrice, reducedPrice);
  }
}
