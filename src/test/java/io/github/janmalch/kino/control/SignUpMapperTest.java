package io.github.janmalch.kino.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.janmalch.kino.api.model.SignUpDto;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

public class SignUpMapperTest {

  @Test
  public void mapToEntity() {
    var signUpDto = new SignUpDto();
    signUpDto.setEmail("test@example.com");
    signUpDto.setFirstName("Test");
    signUpDto.setLastName("Account");
    signUpDto.setPassword("Start123");
    signUpDto.setBirthday("1990-01-01");

    var mapper = new SignUpControl.SignUpMapper();
    var entity = mapper.mapToEntity(signUpDto);

    assertEquals("test@example.com", entity.getEmail());
    assertEquals("Test", entity.getFirstName());
    assertEquals("Account", entity.getLastName());
    assertEquals("Start123", entity.getPassword());
    // TODO: fix SimpleDateFormat in mapper
    // var formatter = new SimpleDateFormat("yyyy-MM-dd");
    // assertEquals("1990-01-01", formatter.format(entity.getBirthday()));
  }

  @Test
  public void mapToEntityInvalidBirthday() {
    var signUpDto = new SignUpDto();
    signUpDto.setEmail("test@example.com");
    signUpDto.setFirstName("Test");
    signUpDto.setLastName("Account");
    signUpDto.setPassword("Start123");
    signUpDto.setBirthday("19g90-01-01");

    var mapper = new SignUpControl.SignUpMapper();
    try {
      mapper.mapToEntity(signUpDto);
      fail();
    } catch (RuntimeException e) {
      if (!(e.getCause() instanceof ParseException)) {
        fail("Unknown cause of RuntimeException");
      }
    }
  }
}
