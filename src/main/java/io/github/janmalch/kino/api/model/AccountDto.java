package io.github.janmalch.kino.api.model;

import io.github.janmalch.kino.entity.Role;

public class AccountDto extends SignUpDto {
  private Role role;
  private long id;

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
    return "AccountDto{"
        + "id='"
        + id
        + '\''
        + ", firstName='"
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
