package io.github.janmalch.kino.security;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import java.security.Principal;
import java.util.Optional;
import javax.ws.rs.core.SecurityContext;

public class TokenSecurityContext implements SecurityContext {
  private final Token token;
  private Role userRole;

  public TokenSecurityContext(Token receivedToken) {
    this.token = ensureUserExists(receivedToken);
  }

  Token ensureUserExists(Token receivedToken) {
    if (receivedToken == null) {
      return null;
    }

    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var query = new AccountByEmailSpec(receivedToken.getName(), repository);
    Optional<Account> referredUser;
    try {
      referredUser = repository.queryFirst(query);
    } finally {
      repository.close();
    }
    if (referredUser.isEmpty()) {
      return null;
    }

    this.userRole = referredUser.get().getRole();
    return receivedToken;
  }

  @Override
  public Principal getUserPrincipal() {
    return this.token;
  }

  @Override
  public boolean isUserInRole(String s) {
    if (userRole == null) {
      return false;
    }
    try {
      Role requestedRole = Role.valueOf(s);
      return userRole.hasMinRole(requestedRole);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public boolean isSecure() {
    return false;
  }

  @Override
  public String getAuthenticationScheme() {
    return "JWT";
  }
}
