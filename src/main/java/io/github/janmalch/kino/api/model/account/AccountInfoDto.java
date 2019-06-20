package io.github.janmalch.kino.api.model.account;

import io.github.janmalch.kino.entity.Role;

public class AccountInfoDto extends SignUpDto {
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
    return "AccountInfoDto{"
        + "id='"
        + id
        + '\''
        + ", firstName='"
        + getFirstName()
        + '\''
        + ", lastName='"
        + getLastName()
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
