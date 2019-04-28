package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.Token;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetMyAccountControl implements Control<Account> {

  private Logger log = LoggerFactory.getLogger(GetMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final Token token;

  public GetMyAccountControl(Token token) {
    this.token = token;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Account> result) {
    log.info("Retrieving my Account " + token.getName());

    Specification<Account> myName = new UserByEmailSpec(token.getName());
    var myAccount = repository.queryFirst(myName);

    if (myAccount.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot retrieve my Account")
              .instance()
              .build());
    }
    return result.success(myAccount.get());
  }
}
