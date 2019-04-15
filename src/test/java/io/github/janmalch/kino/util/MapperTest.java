package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapperTest {

  @Test
  @DisplayName("mapFromEntity should fail if not overwritten")
  void mapFromEntity() {
    var mapper = new TestMapper();
    try {
      mapper.mapFromEntity(new Object());
      fail();
    } catch (UnsupportedOperationException e) {
      // expected when not overwritten
    }
  }

  @Test
  @DisplayName("mapToEntity should fail if not overwritten")
  void mapToEntity() {
    var mapper = new TestMapper();
    try {
      mapper.mapToEntity(new Object());
      fail();
    } catch (UnsupportedOperationException e) {
      // expected when not overwritten
    }
  }

  private static class TestMapper implements Mapper<Object, Object> {}
}
