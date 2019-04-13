package io.github.janmalch.kino.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {

  public byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  public String hashPassword(String clearPassword, byte[] salt) {
    String generatedHashedPassword;
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("SHA-512");

    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    assert md != null;
    md.update(salt);
    byte[] bytes = md.digest(clearPassword.getBytes(StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    // https://stackoverflow.com/questions/36491665/byte-to-integer-and-then-to-string-conversion-in-java
    for (int i = 0; i < bytes.length; i++) {
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
    }
    generatedHashedPassword = sb.toString();

    return generatedHashedPassword;
  }

  public boolean isSamePassword(String clearPassword, String hash, byte[] salt) {
    return hash.equals(hashPassword(clearPassword, salt));
  }
}
