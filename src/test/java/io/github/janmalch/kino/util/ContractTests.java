package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ContractTests {

  @Test()
  public void require() {
    try {
      int argument = -1;
      Contract.require(argument > 0, "argument must be greater than zero");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("argument must be greater than zero", e.getMessage());
    }
  }

  @Test()
  public void check() {
    try {
      int state = -1;
      Contract.check(state > 0, "state must be greater than zero");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("state must be greater than zero", e.getMessage());
    }
  }

  @Test()
  public void affirm() {
    try {
      int result = -1;
      Contract.affirm(result > 0, "result can't be negative");
      fail();
    } catch (AssertionError e) {
      assertEquals("result can't be negative", e.getMessage());
    }
  }
}
