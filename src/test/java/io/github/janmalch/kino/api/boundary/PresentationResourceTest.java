package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.janmalch.kino.api.model.presentation.NewPresentationDto;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.problem.ThrowableProblem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PresentationResourceTest {
  @BeforeEach
  public void tearDown() {
    var ew = new EntityWiper();
    ew.wipeDB();
  }

  @Test
  void getAllPresentations() {
    // uses Generic Control internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    resource.getAllPresentations();
  }

  @Test
  void getPresentation() {
    // uses Generic Control internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    assertThrows(ThrowableProblem.class, () -> resource.getPresentation(100L));
  }

  @Test
  void newPresentation() {
    // uses Generic Control internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    resource.newPresentation(new NewPresentationDto());
  }

  @Test
  void updatePresentation() {
    // uses Generic Control internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    assertThrows(
        ThrowableProblem.class, () -> resource.updatePresentation(new NewPresentationDto(), 100L));
  }

  @Test
  void deletePresentation() {
    // uses Generic Control internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    assertThrows(ThrowableProblem.class, () -> resource.deletePresentation(100L));
  }
}
