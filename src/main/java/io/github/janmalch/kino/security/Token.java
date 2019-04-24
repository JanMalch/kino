package io.github.janmalch.kino.security;

import java.security.Principal;
import java.util.Date;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

public interface Token extends Principal {
  String getTokenString();

  String getSubject() throws InvalidJwtException, MalformedClaimException;

  @Override
  default String getName() {
    try {
      return getSubject();
    } catch (Exception e) {
      // getSubject throws InvalidJwtException, MalformedClaimException
      // but can't change getName() method signature
      // rethrow as unchecked exception
      throw new RuntimeException(e);
    }
  }

  boolean isExpired();

  Date getExpiration();
}
