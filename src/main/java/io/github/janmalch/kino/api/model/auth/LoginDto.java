package io.github.janmalch.kino.api.model.auth;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDto {

  @NotNull
  @Size(min = 1)
  private String email;

  @NotNull
  @Size(min = 1)
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginDto{" + "email='" + email + '\'' + ", password='" + password + '\'' + '}';
  }
}
