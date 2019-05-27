package io.github.janmalch.kino.repository.specification;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.RepositoryFactory;
import org.junit.jupiter.api.Test;

class AccountByEmailSpecTest {

  @Test
  void toQuery() {
    var repo = RepositoryFactory.createRepository(Account.class);
    var spec = new AccountByEmailSpec("test@example.com", repo);
    assertNotNull(spec.toQuery());
  }
}
