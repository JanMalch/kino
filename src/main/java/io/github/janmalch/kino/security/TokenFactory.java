package io.github.janmalch.kino.security;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

public interface TokenFactory {
  Token generateToken(String subject);

  Token parse(String token) throws InvalidJwtException, MalformedClaimException;

  Token refresh(Token token) throws MalformedClaimException, InvalidJwtException;

  void setTokenDuration(long duration);

  Token invalidate();
}
