package io.github.janmalch.kino.security;

import io.github.janmalch.kino.entity.Account;
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

class TokenFactory {

  private Logger log = LoggerFactory.getLogger(TokenFactory.class);

  private static final String ROLE_KEY = "sub";
  private static final String secretKey = "jAzbOw76gakypHAYOsn5";
  private long tokenDuration = TimeUnit.HOURS.toMillis(1); // duration = 1h

  Token createToken(Account account) {
    long now = (new Date()).getTime();
    Token token = new Token();

    token.setToken(
        Jwts.builder()
            .setSubject(account.getEmail())
            .claim(ROLE_KEY, account.getRole())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(new Date(now + this.tokenDuration))
            .compact());

    log.info("Token created for " + account.getEmail());
    return token;
  }

  String getAccountRole(Token token) throws InvalidJwtException, MalformedClaimException {
    JwtConsumer consumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();
    JwtClaims claims = consumer.processToClaims(token.getToken());

    return claims.getSubject();
  }

  boolean validateToken(Token authToken) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken.getToken());
      return true;
    } catch (ExpiredJwtException e) {
      log.info("Authentication Token has expired");
      return false;
    }
  }
}
