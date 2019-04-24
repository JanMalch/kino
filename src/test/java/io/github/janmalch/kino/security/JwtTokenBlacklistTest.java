package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import java.util.concurrent.TimeUnit;
import org.jose4j.jwt.MalformedClaimException;
import org.junit.jupiter.api.Test;

class JwtTokenBlacklistTest {

  @Test
  void getInstance() {
    var blacklist1 = JwtTokenBlacklist.getInstance();
    var blacklist2 = JwtTokenBlacklist.getInstance();

    assertEquals(blacklist1, blacklist2);
  }

  @Test
  void doesntAddExpiredToken() {
    var blacklist = JwtTokenBlacklist.getInstance();
    JwtTokenFactory factory = new JwtTokenFactory();
    factory.setTokenDuration(TimeUnit.SECONDS.toMillis(-10));
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    var expiredToken = factory.generateToken(acc.getEmail());
    try {
      blacklist.addToBlackList(expiredToken.getTokenString());
    } catch (MalformedClaimException e) {
      if (!(e.getCause() instanceof MalformedClaimException)) {
        fail("Unknown RuntimeException");
      }
    }
  }

  @Test
  void addToBlackList() throws MalformedClaimException {
    var blacklist = JwtTokenBlacklist.getInstance();
    JwtTokenFactory factory = new JwtTokenFactory();

    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    Account acc2 = new Account();
    acc.setEmail("TestUser2@mail.de");

    Token token = factory.generateToken(acc.getEmail());
    Token token2 = factory.generateToken(acc2.getEmail());

    blacklist.addToBlackList(token.getTokenString());
    assertTrue(blacklist.isBlacklisted(token));
    assertFalse(blacklist.isBlacklisted(token2));
  }
}
