package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.janmalch.kino.api.model.pricecategory.PriceCategoryBaseDto;
import io.github.janmalch.kino.api.model.pricecategory.PriceCategoryDto;
import io.github.janmalch.kino.problem.ThrowableProblem;
import org.junit.jupiter.api.Test;

class PriceCategoryResourceTest {

  // uses generic controls internally and other tested classes
  // test is for unexpected Exceptions when combining them
  private PriceCategoryResource resource = new PriceCategoryResource();

  @Test
  void getAllPriceCategories() {
    var result = resource.getAllPriceCategories();
    assertNotNull(result);
  }

  @Test
  void getPriceCategory() {
    assertThrows(ThrowableProblem.class, () -> resource.getPriceCategory(1L));
  }

  @Test
  void createPriceCategory() {
    var result = resource.createPriceCategory(new PriceCategoryBaseDto());
    assertNotNull(result);
  }

  @Test
  void updatePriceCategory() {
    assertThrows(
        ThrowableProblem.class, () -> resource.updatePriceCategory(new PriceCategoryDto(), 1L));
  }

  @Test
  void deletePriceCategory() {
    assertThrows(ThrowableProblem.class, () -> resource.deletePriceCategory(1L));
  }
}
