package io.github.janmalch.kino.api.model;

public class AccountDto extends AccountInfoDto {

  @Override
  public String toString() {
    return "AccountDto{"
        + "id='"
        + getId()
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
