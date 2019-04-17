package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import org.junit.jupiter.api.Test;

class UserRepositoryTest {

  @Test
  void find() {
    var repository = new UserRepository();
    var account = new Account();
    account.setEmail("test@example.com");
    repository.add(account);
    var accountId = account.getId();

    var fetched = repository.find(accountId);
    assertEquals("test@example.com", fetched.getEmail());
  }

  @Test
  void getEntityManager() {
    var repository = new UserRepository();
    assertNotNull(repository.getEntityManager());
  }
}
