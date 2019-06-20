package io.github.janmalch.kino.control.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.api.model.account.SignUpDto;
import io.github.janmalch.kino.security.PasswordManager;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SignUpMapperTest {

  @Test
  void map() {
    var bday = LocalDate.now();
    var signUpDto = new SignUpDto();
    var pm = new PasswordManager();
    signUpDto.setEmail("test@example.com");
    signUpDto.setFirstName("Test");
    signUpDto.setLastName("Account");
    signUpDto.setPassword("Start123");
    signUpDto.setBirthday(bday);

    var mapper = new SignUpControl.SignUpMapper();
    var entity = mapper.map(signUpDto);

    assertEquals("test@example.com", entity.getEmail());
    assertEquals("Test", entity.getFirstName());
    assertEquals("Account", entity.getLastName());
    assertEquals(pm.hashPassword("Start123", entity.getSalt()), entity.getHashedPassword());
    assertEquals(bday, entity.getBirthday());
  }
}
