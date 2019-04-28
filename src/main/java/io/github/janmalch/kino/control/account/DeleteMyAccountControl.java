package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.Token;
import java.util.NoSuchElementException;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMyAccountControl implements Control<Token> {

  private Logger log = LoggerFactory.getLogger(DeleteMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();

  private final Token token;

  public DeleteMyAccountControl(Token token) {
    this.token = token;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Token> result) {
    log.info("Deleting my Account " + token.getName());

    Specification<Account> myName = new UserByEmailSpec(token.getName());
    var account = repository.queryFirst(myName);
    try {
      repository.remove(account.get());
    } catch (NoSuchElementException e) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot delete my Account")
              .instance()
              .build());
    }

    blacklist.addToBlackList(token);
    var expiredToken = factory.invalidate();

    // TODO delete Reservation and Co

    return result.success(expiredToken);
  }
}