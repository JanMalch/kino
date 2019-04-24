package io.github.janmalch.kino.api.boundary;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.repository.UserRepository;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class UserResourceTest {

  @Test
  void signUp() {
    var resource = new UserResource();
    var dto = new SignUpDto();
    dto.setEmail("signUp@example.com");
    dto.setFirstName("Test");
    dto.setLastName("Dude");
    dto.setBirthday(LocalDate.now());
    dto.setPassword("Start123");
    var response = resource.signUp(dto);
    assertEquals(200, response.getStatus());

    // check if user exists
    var repository = new UserRepository();
    var spec = new UserByEmailSpec("signUp@example.com");
    var account = repository.queryFirst(spec);
    assertTrue(account.isPresent());
    assertNotEquals("Start123", account.get().getHashedPassword());
  }
}
