package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MapperTest {

  @Test
  void update() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> new TestMapperTest().update(1L, "2"),
        "Interface should provide default implementation to minimize duplicate code");
  }

  @Test
  void map() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> new TestMapperTest().map(1L),
        "Interface should provide default implementation to minimize duplicate code");
  }

  private static class TestMapperTest implements Mapper<Long, String> {}
}
