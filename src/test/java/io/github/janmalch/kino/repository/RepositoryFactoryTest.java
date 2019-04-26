package io.github.janmalch.kino.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janmalch.kino.entity.Account;
import org.junit.jupiter.api.Test;

class RepositoryFactoryTest {

  @Test
  void createRepository() {
    Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
    var account = new Account();
    account.setEmail("test@example.com");
    repository.add(account);

    var id = account.getId();

    var fetched = repository.find(id);
    assertEquals(account.getEmail(), fetched.getEmail());
  }
}
