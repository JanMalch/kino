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

class Token {

  private Logger log = LoggerFactory.getLogger(Token.class);

  private static final String ROLE_KEY = "sub";
  private static final String secretKey = "jAzbOw76gakypHAYOsn5";
  private long tokenDuration = TimeUnit.HOURS.toMillis(10); // duration = 10h

  String createToken(Account account) {
    long now = (new Date()).getTime();
    String token =
        Jwts.builder()
            .setSubject(account.getEmail())
            .claim(ROLE_KEY, account.getRole())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .setExpiration(new Date(now + this.tokenDuration))
            .compact();

    log.info("Token created for " + account.getEmail());
    return token;
  }

  String getAccountCredential(String token) throws InvalidJwtException, MalformedClaimException {
    JwtConsumer consumer =
        new JwtConsumerBuilder()
            .setSkipAllValidators()
            .setDisableRequireSignature()
            .setSkipSignatureVerification()
            .build();
    JwtClaims claims = consumer.processToClaims(token);

    return claims.getSubject();
  }

  boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
      return true;
    } catch (ExpiredJwtException e) {
      log.info("Authentikation Token has expired");
      return false;
    }
  }
}
