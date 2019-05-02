package io.github.janmalch.kino.api.model;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Role;
import org.junit.jupiter.api.Test;

class AccountInfoDtoTest {

  @Test
  void getRole() {
    AccountInfoDto info = new AccountInfoDto();
    info.setRole(Role.CUSTOMER);
    assertEquals(Role.CUSTOMER, info.getRole());
  }

  @Test
  void toString1() {
    AccountInfoDto info = new AccountInfoDto();
    info.setRole(Role.CUSTOMER);
    assertTrue(info.toString().contains("CUSTOMER"));
  }
}
