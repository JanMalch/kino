package io.github.janmalch.kino.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RoleTest {
  @Test
  void hasMinRole() {
    Role testRole = Role.ADMIN;
    assertTrue(testRole.hasMinRole(Role.GUEST));
    assertTrue(testRole.hasMinRole(Role.CUSTOMER));
    assertTrue(testRole.hasMinRole(Role.MODERATOR));
    assertTrue(testRole.hasMinRole(Role.ADMIN));

    testRole = Role.CUSTOMER;
    assertTrue(testRole.hasMinRole(Role.GUEST));
    assertTrue(testRole.hasMinRole(Role.CUSTOMER));
    assertFalse(testRole.hasMinRole(Role.MODERATOR));
    assertFalse(testRole.hasMinRole(Role.ADMIN));

    testRole = Role.GUEST;
    assertTrue(testRole.hasMinRole(Role.GUEST));
    assertFalse(testRole.hasMinRole(Role.CUSTOMER));
    assertFalse(testRole.hasMinRole(Role.MODERATOR));
    assertFalse(testRole.hasMinRole(Role.ADMIN));
  }
}
