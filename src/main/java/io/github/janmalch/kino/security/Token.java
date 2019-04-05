package io.github.janmalch.kino.security;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

public interface Token {
  String getTokenString();

  String getSubject() throws InvalidJwtException, MalformedClaimException;

  boolean isExpired();
}
