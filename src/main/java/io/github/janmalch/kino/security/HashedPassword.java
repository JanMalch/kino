package io.github.janmalch.kino.security;

public class HashedPassword {

  private final String hash;
  private final byte[] salt;

  public HashedPassword(String hash, byte[] salt) {
    this.hash = hash;
    this.salt = salt;
  }

  String getHash() {
    return this.hash;
  }

  byte[] getSalt() {
    return this.salt;
  }
}
