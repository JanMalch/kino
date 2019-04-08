package io.github.janmalch.kino.security;

import io.github.janmalch.kino.entity.Role;
import io.github.janmalch.kino.repository.UserRepository;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

public class TokenSecurityContext implements SecurityContext {
  private final Token token;
  private Role userRole;

  public TokenSecurityContext(Token token) {
    this.token = ensureUserExists(token);
  }

  Token ensureUserExists(Token token) {
    UserRepository repository = new UserRepository();

    var query = new UserByEmailSpec(this.token.getName());
    var referredUser = repository.queryFirst(query);

    if (referredUser.isPresent()) {
      this.userRole = referredUser.get().getRole();
      return token;
    }

    return null;
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

    // UserRepository repository = new UserRepository();
    //
    // var query = new UserByEmailSpec(this.token.getName());
    // var referredUser = repository.queryFirst(query);
    // Contract.check(referredUser.isPresent(), "User referred in token does not exist");
    //
    // var userRole = referredUser.get().getRole().name();
    //
    // return Arrays.stream(Role.values()).anyMatch(role -> role.name().equals(userRole));

    // var user = referredUser.get();
    // var userRole = user.getRole();
    // for (Role r : Role.values()) {
    //     if (r.name().equals(s)) {
    //         return userRole.hasMinRole(r);
    //     }
    // }
    // return false;
  }

  @Override
  public boolean isSecure() {
    return false;
  }

  @Override
  public String getAuthenticationScheme() {
    return null;
  }
}
