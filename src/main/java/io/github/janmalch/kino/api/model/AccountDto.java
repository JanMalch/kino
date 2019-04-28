package io.github.janmalch.kino.api.model;

import io.github.janmalch.kino.entity.Role;
import java.time.LocalDate;
import javax.json.bind.annotation.JsonbDateFormat;

public class AccountDto {

  private String firstName;
  private String lastName;
  private String password;
  private String email;

  private Role role;

  @JsonbDateFormat(value = "yyyy-MM-dd", locale = "de-DE")
  private LocalDate birthday;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
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
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", password='"
        + password
        + '\''
        + ", email='"
        + email
        + '\''
        + ", birthday="
        + birthday
        + '\''
        + ", Role='"
        + role
        + '}';
  }
}
