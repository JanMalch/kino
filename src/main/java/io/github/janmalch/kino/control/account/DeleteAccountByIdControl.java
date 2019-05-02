package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import javax.ws.rs.core.Response;
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
    var repoAcc = repository.find(id);
    if (repoAcc == null) {
      return result.failure(
          Problem.builder(Response.Status.NOT_FOUND)
              .type("Cannot delete Account")
              .instance()
              .build());
    }
    repository.remove(repoAcc);
    return result.success(null, "Account successfully deleted");
  }
}
