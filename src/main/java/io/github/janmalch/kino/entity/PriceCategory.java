package io.github.janmalch.kino.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PriceCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  private float regularPrice;

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
