package io.github.janmalch.kino.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoleTest {
  @Test
  void getAllChildren() {
    List<Role> expectedList = new ArrayList<>();
    expectedList.add(Role.ADMIN);
    List<Role> actual = Role.MODERATOR.getAllChildren();
    assertEquals(actual, expectedList);
  }

  @Test
  void getAllChildren2() {
    List<Role> expectedList = new ArrayList<>();
    expectedList.add(Role.MODERATOR);
    expectedList.add(Role.ADMIN);
    List<Role> actual = Role.CUSTOMER.getAllChildren();
    assertEquals(actual, expectedList);
  }
}
