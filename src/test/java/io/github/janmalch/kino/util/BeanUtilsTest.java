package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class BeanUtilsTest {

  @Test
  void isNullOrEmpty() {
    assertTrue(BeanUtils.isNullOrEmpty(null));
    assertTrue(BeanUtils.isNullOrEmpty(""));
    assertTrue(BeanUtils.isNullOrEmpty(new HashSet<>()));
    assertFalse(BeanUtils.isNullOrEmpty(new HashSet<>().add("a")));
    assertFalse(BeanUtils.isNullOrEmpty("a"));
    assertFalse(BeanUtils.isNullOrEmpty(5));
    assertFalse(BeanUtils.isNullOrEmpty(false));
  }
}
