package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ContractTests {

  @Test
  public void require() {
    try {
      int argument = -1;
      Contract.require(argument > 0, "argument must be greater than zero");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("argument must be greater than zero", e.getMessage());
    }
  }

  @Test
  public void requirePassing() {
    try {
      int argument = 1;
      Contract.require(argument > 0, "argument must be greater than zero");
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void check() {
    try {
      int state = -1;
      Contract.check(state > 0, "state must be greater than zero");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("state must be greater than zero", e.getMessage());
    }
  }

  @Test
  public void checkPassing() {
    try {
      int state = 1;
      Contract.check(state > 0, "state must be greater than zero");
    } catch (IllegalStateException e) {
      fail();
    }
  }

  @Test
  public void affirm() {
    try {
      int result = -1;
      Contract.affirm(result > 0, "result can't be negative");
      fail();
    } catch (AssertionError e) {
      assertEquals("result can't be negative", e.getMessage());
    }
  }

  @Test
  public void affirmPassing() {
    try {
      int result = 1;
      Contract.affirm(result > 0, "result can't be negative");
    } catch (AssertionError e) {
      fail();
    }
  }
}
