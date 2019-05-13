package io.github.janmalch.kino.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class Account implements Identifiable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  private String firstName;

  private String lastName;

  @Email private String email;

  private LocalDate birthday;

  private Role role;

  private byte[] salt;

  private String hashedPassword;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public byte[] getSalt() {
    return salt;
  }

  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPassword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  @Override
  public String toString() {
    return "Account{"
        + "id="
        + id
        + ", reservations="
        + reservations
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", birthday="
        + birthday
        + ", role="
        + role
        + ", salt="
        + Arrays.toString(salt)
        + ", hashedPassword='"
        + hashedPassword
        + '\''
        + '}';
  }
}
