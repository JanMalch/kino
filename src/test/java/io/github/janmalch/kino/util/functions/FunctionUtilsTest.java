package io.github.janmalch.kino.util.functions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FunctionUtilsTest {

  private int throwIfZero(int input) throws IllegalArgumentException {
    if (input == 0) {
      throw new IllegalArgumentException();
    }
    return input;
  }

  @Test
  void uncheck() {
    FunctionWithException<Integer, Integer, IllegalArgumentException> fn = this::throwIfZero;

    try {
      FunctionUtils.uncheck(fn).apply(0);
      fail("Should throw RuntimeException");
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof IllegalArgumentException)) {
        fail("RuntimeException should be caused by IllegalArgumentException");
      }
    }

    try {
      var result = FunctionUtils.uncheck(fn).apply(10);
      assertEquals(10, result);
    } catch (RuntimeException e) {
      fail("Should not throw RuntimeException");
    }
  }

  @Test
  void uncheck1() {
    SupplierWithException<Integer, IllegalArgumentException> fnEx = () -> this.throwIfZero(0);

    try {
      FunctionUtils.uncheck(fnEx);
      fail("Should throw RuntimeException");
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof IllegalArgumentException)) {
        fail("RuntimeException should be caused by IllegalArgumentException");
      }
    }

    SupplierWithException<Integer, IllegalArgumentException> fn = () -> this.throwIfZero(10);
    try {
      var result = FunctionUtils.uncheck(fn);
      assertEquals(10, result);
    } catch (RuntimeException e) {
      fail("Should not throw RuntimeException");
    }
  }

  @Test
  void uncheck2() {
    VoidConsumerWithException<IllegalArgumentException> fnEx =
        () -> {
          this.throwIfZero(0);
        };

    try {
      FunctionUtils.uncheck(fnEx);
      fail("Should throw RuntimeException");
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof IllegalArgumentException)) {
        fail("RuntimeException should be caused by IllegalArgumentException");
      }
    }

    VoidConsumerWithException<IllegalArgumentException> fn =
        () -> {
          this.throwIfZero(10);
        };
    try {
      FunctionUtils.uncheck(fn);
    } catch (RuntimeException e) {
      fail("Should not throw RuntimeException");
    }
  }
}
