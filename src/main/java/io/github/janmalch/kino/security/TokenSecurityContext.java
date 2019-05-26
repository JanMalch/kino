package io.github.janmalch.kino.security;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class TokenSecurityContext implements SecurityContext {
  private final Token token;
  private Role userRole;

  public TokenSecurityContext(Token token) {
    this.token = ensureUserExists(token);
  }

  Token ensureUserExists(Token _token) {
    if (_token == null) {
      return null;
    }

    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

    var query = new AccountByEmailSpec(_token.getName(), repository);
    var referredUser = repository.queryFirst(query);
    repository.getEntityManager().close();
    if (referredUser.isEmpty()) {
      return null;
    }

    this.userRole = referredUser.get().getRole();
    return _token;
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
