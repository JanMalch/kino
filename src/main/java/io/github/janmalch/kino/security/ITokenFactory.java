package io.github.janmalch.kino.security;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

public interface ITokenFactory {
  Token generateToken(String subject);

  Token parse(String token) throws InvalidJwtException, MalformedClaimException;

  void setTokenDuration(long duration);
}
