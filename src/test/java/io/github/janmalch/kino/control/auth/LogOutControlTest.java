package io.github.janmalch.kino.control.auth;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class LogOutControlTest {

  @Test
  void executeWithInvalidToken() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    factory.setTokenDuration(TimeUnit.SECONDS.toMillis(-10));
    var expiredToken = factory.generateToken(acc.getEmail());

    var control = new LogOutControl(expiredToken);
    var response = control.execute(new EitherResultBuilder<>());
    assertEquals(200, response.getStatus().getStatusCode());
  }

  @Test
  void getInvalidedTokenForLogout() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Account acc = new Account();
    acc.setEmail("TestUser@mail.de");
    var expiredToken = factory.generateToken(acc.getEmail());

    var control = new LogOutControl(expiredToken);
    var response = control.execute(new EitherResultBuilder<>());
    assertEquals(200, response.getStatus().getStatusCode());
  }
}
