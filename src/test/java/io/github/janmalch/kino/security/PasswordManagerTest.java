package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordManagerTest {

  private PasswordManager pm = new PasswordManager();

  @Test
  void generateSalt() {
    byte[] salt = pm.generateSalt();
    assertNotNull(salt);
    assertNotEquals(0, salt.length);
  }

  @Test
  void hashPassword_checkResultingHashesAreEqual() {
    byte[] salt = pm.generateSalt();
    String hash1 = pm.hashPassword("testPassword", salt);
    String hash2 = pm.hashPassword("testPassword", salt);
    assertEquals(hash1, hash2);
  }

  @Test
  void hashPassword_checkResultingWithDifferentSalts() {
    byte[] salt1 = pm.generateSalt();
    byte[] salt2 = pm.generateSalt();
    String hash1 = pm.hashPassword("testPassword", salt1);
    String hash2 = pm.hashPassword("testPassword", salt2);
    assertNotEquals(hash1, hash2);
  }

  @Test
  void isSamePassword_withCorrectSaltAndPassword() {
    byte[] salt = pm.generateSalt();
    String hash = pm.hashPassword("testPassword", salt);

    assertTrue(pm.isSamePassword("testPassword", hash, salt));
  }

  @Test
  void isSamePassword_withIncorrectSaltAndPassword() {
    byte[] salt = pm.generateSalt();
    String hash = pm.hashPassword("testPassword", salt);

    assertFalse(pm.isSamePassword("testWrongPassword", hash, salt));
  }
}
