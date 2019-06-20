package io.github.janmalch.kino.api.model.account;

import io.github.janmalch.kino.entity.Role;

public class SafeAccountInfoDto extends AbstractAccountDto {

  private long id;
  private Role role;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "SafeAccountInfoDto{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", role="
        + role
        + '}';
  }
}
