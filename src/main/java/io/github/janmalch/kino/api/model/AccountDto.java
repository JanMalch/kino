package io.github.janmalch.kino.api.model;

import io.github.janmalch.kino.entity.Role;

public class AccountDto extends SignUpDto {
  private Role role;

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "AccountDto{"
        + "firstName='"
        + getFirstName()
        + '\''
        + ", lastName='"
        + getLastName()
        + '\''
        + ", password='"
        + getPassword()
        + '\''
        + ", email='"
        + getEmail()
        + '\''
        + ", birthday="
        + getBirthday()
        + '\''
        + ", Role="
        + getRole()
        + '}';
  }
}
