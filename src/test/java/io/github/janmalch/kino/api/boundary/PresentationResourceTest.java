package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.janmalch.kino.problem.ThrowableProblem;
import org.junit.jupiter.api.Test;

class PresentationResourceTest {

  @Test
  void getPresentation() {
    // uses GetEntityControl internally and other tested classes
    // test is for unexpected Exceptions when combining them
    var resource = new PresentationResource();
    assertThrows(ThrowableProblem.class, () -> resource.getPresentation(100L));
  }
}
