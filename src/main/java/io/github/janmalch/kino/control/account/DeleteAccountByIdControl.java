package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAccountByIdControl implements Control<Void> {
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private Logger log = LoggerFactory.getLogger(DeleteAccountByIdControl.class);

  private final long id;

  public DeleteAccountByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    log.info("Deleting Account ID: " + id);
    var account = Problems.requireEntity(repository.find(id), id, "No account found");
    repository.remove(account);
    return result.success("Account successfully deleted");
  }
}
