package io.github.janmalch.kino.security;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;

/**
 * Interface to define all methods necessary to implement token authentication. The factory should
 * be stateless.
 */
public interface TokenFactory {
  /**
   * Generate a token instance for the given subject, who this token is for
   *
   * @param subject the subject to create the token for
   * @return a valid token for the given subject
   */
  Token generateToken(String subject);

  /**
   * Parses a transmitted token string and generates a token instance from it.
   *
   * @param token the token string
   * @return a token instance from the parsed token string
   * @throws InvalidJwtException thrown when a JWT is considered invalid or otherwise cannot be
   *     processed/consumed
   * @throws MalformedClaimException thrown when a JWT cannot be parsed
   */
  Token parse(String token) throws InvalidJwtException, MalformedClaimException;

  /**
   * Creates a copy of the given token and extends it expiration date, by the default duration.
   *
   * @param token the token to be refreshed
   * @return new token instance with refreshed expiration date
   * @throws InvalidJwtException thrown when a JWT is considered invalid or otherwise cannot be
   *     processed/consumed
   * @throws MalformedClaimException thrown when a JWT cannot be parsed
   */
  Token refresh(Token token) throws MalformedClaimException, InvalidJwtException;

  /**
   * Sets the default duration for new or refreshed tokens
   *
   * @param duration a duration in milliseconds
   */
  void setTokenDuration(long duration);

  /**
   * Generates an expired token for an undefined subject
   *
   * @return a new token instance
   */
  Token invalidate();
}
