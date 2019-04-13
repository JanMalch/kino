package io.github.janmalch.kino.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {

  byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[16];
    random.nextBytes(salt);
    return salt;
  }

  HashedPassword hashPassword(String clearPassword, byte[] salt) {
    String generatedHashedPassword = "";
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");

      md.update(salt);
      byte[] bytes = md.digest(clearPassword.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      // https://stackoverflow.com/questions/36491665/byte-to-integer-and-then-to-string-conversion-in-java
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedHashedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return new HashedPassword(generatedHashedPassword, salt);
  }

  boolean isSamePassword(String clearPassword, String hash, byte[] salt) {
    return hash.equals(hashPassword(clearPassword, salt));
  }
}
