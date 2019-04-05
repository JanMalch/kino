package io.github.janmalch.kino.security;

import io.jsonwebtoken.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TokenFactory implements ITokenFactory {

  private Logger log = LoggerFactory.getLogger(TokenFactory.class);

  private long tokenDuration = TimeUnit.HOURS.toMillis(1);
  private static final String secretKey = "jAzbOw76gakypHAYOsn5";
  private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

  @Override
  public Token generateToken(String subject) {
    long now = System.currentTimeMillis();

    var expiration = new Date(now + this.tokenDuration);
    var tokenString =
        Jwts.builder()
            .setSubject(subject)
            .signWith(algorithm, secretKey)
            .setExpiration(expiration)
            .compact();
    return new JwtToken(tokenString, subject, expiration);
  }

  @Override
  public Token refresh(Token token) throws MalformedClaimException, InvalidJwtException {
    Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.getTokenString());

    JwtConsumer consumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
    JwtClaims claims = consumer.processToClaims(token.getSubject());
    var subject = claims.getSubject();
    var expiration = new Date(System.currentTimeMillis() + this.tokenDuration);
    log.info(subject + " has refreshed his Token");
    return new JwtToken(token.toString(), subject, expiration);
  }

  @Override
  public Token parse(String token)
      throws InvalidJwtException, MalformedClaimException, SignatureException {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token); // signatur prüfen
    } catch (ExpiredJwtException e) {
      // token is still valid, just expired
      // this can be expected
    }
    JwtConsumer consumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
    JwtClaims claims = consumer.processToClaims(token);
    var subject = claims.getSubject();
    var expiration =
        new Date(claims.getExpirationTime().getValueInMillis() - TimeUnit.HOURS.toMillis(10));
    return new JwtToken(token, subject, expiration);
  }

  @Override
  public void setTokenDuration(long duration) {
    this.tokenDuration = duration;
  }
}
