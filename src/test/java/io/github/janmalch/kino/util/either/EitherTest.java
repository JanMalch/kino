package io.github.janmalch.kino.util.either;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EitherTest {

  @Test
  void equals1() {
    var success = 1;
    var either1 = new Either<>(success);
    var either2 = new Either<>(success);
    assertEquals(either1, either2);
  }

  @Test
  void equals2() {
    var success = 1;
    var either1 = new Either<>(success);
    var either2 = either1;
    assertEquals(either1, either2);
  }

  @Test
  void equals3() {
    var success = 1;
    var either1 = new Either<>(success);
    assertNotEquals(either1, success);
    assertNotEquals(either1, null);
  }

  @Test
  void hashCode1() {
    var success = 1;
    var either1 = new Either<>(success);
    var either2 = new Either<>(success);
    assertEquals(either1.hashCode(), either2.hashCode());
  }

  @Test
  void toString1() {
    var either = new Either<>(1);
    assertTrue(either.toString().contains("isSuccess"));
  }
}
