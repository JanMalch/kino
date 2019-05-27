package io.github.janmalch.kino.control.myaccount;

import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMyAccountControl extends ManagingControl<Token> {

  private Logger log = LoggerFactory.getLogger(DeleteMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();

  private final Token token;

  public DeleteMyAccountControl(Token token) {
    this.token = token;
  }

  @Override
  public <T> T compute(ResultBuilder<T, Token> result) {
    manage(repository);
    log.info("Deleting my Account " + token.getName());

    Specification<Account> myName = new AccountByEmailSpec(token.getName(), repository);
    var account = repository.queryFirst(myName);

    if (account.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot delete my Account")
              .instance()
              .build());
    }

    repository.remove(account.get());

    blacklist.addToBlackList(token);
    var expiredToken = factory.invalidate();

    return result.success(expiredToken);
  }
}
