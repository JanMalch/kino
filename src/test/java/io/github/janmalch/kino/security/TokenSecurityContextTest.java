package io.github.janmalch.kino.security;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import org.junit.jupiter.api.Test;

class TokenSecurityContextTest {

  @Test
  void ensureUserExistsFailing() {
    JwtTokenFactory factory = new JwtTokenFactory();

    var account = new Account();
    account.setEmail("Test@user.de");
    Token token = factory.generateToken(account.getEmail());

    var context = new TokenSecurityContext(token);
    assertNull(context.ensureUserExists(token));
  }

  @Test
  void ensureUserExists() {
    JwtTokenFactory factory = new JwtTokenFactory();

    var account = new Account();
    account.setEmail("Test@user.de");
    Token token = factory.generateToken(account.getEmail());

    var context = new TokenSecurityContext(token);
    assertNull(context.ensureUserExists(token));
  }

  @Test
  void getUserPrincipal() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var account = new Account();
    account.setEmail("principal@user.de");
    repository.add(account);
    Token token = factory.generateToken(account.getEmail());
    var context = new TokenSecurityContext(token);
    assertEquals("principal@user.de", context.getUserPrincipal().getName());
  }

  @Test
  void isUserInRoleNull() {
    JwtTokenFactory factory = new JwtTokenFactory();

    var account = new Account();
    account.setEmail("Test@user.de");
    Token token = factory.generateToken(account.getEmail());

    var context = new TokenSecurityContext(token);
    assertFalse(context.isUserInRole("MODERATOR"));
  }

  @Test
  void isUserInRole() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var account = new Account();
    account.setEmail("principal2@user.de");
    account.setRole(Role.MODERATOR);
    repository.add(account);
    Token token = factory.generateToken(account.getEmail());
    var context = new TokenSecurityContext(token);
    assertTrue(context.isUserInRole("MODERATOR"));
  }

  @Test
  void isUserInRoleIllegalRoleName() {
    JwtTokenFactory factory = new JwtTokenFactory();
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var account = new Account();
    account.setEmail("principal3@user.de");
    account.setRole(Role.MODERATOR);
    repository.add(account);
    Token token = factory.generateToken(account.getEmail());
    var context = new TokenSecurityContext(token);
    assertFalse(context.isUserInRole("SUPERADMIN"));
  }

  @Test
  void isSecure() {
    var context = new TokenSecurityContext(null);
    assertFalse(context.isSecure(), "Only Hosting HTTP");
  }

  @Test
  void getAuthenticationScheme() {
    var context = new TokenSecurityContext(null);
    assertEquals("JWT", context.getAuthenticationScheme());
  }
}
