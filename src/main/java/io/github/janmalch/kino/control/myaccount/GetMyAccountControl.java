package io.github.janmalch.kino.control.myaccount;

import io.github.janmalch.kino.api.model.account.AccountInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.util.ReflectionMapper;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** If a user wants its own Account info, it needs email as a param. */
public class GetMyAccountControl extends ManagingControl<AccountInfoDto> {

  private Logger log = LoggerFactory.getLogger(GetMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final String email;

  public GetMyAccountControl(String email) {
    this.email = email;
  }

  @Override
  public <T> T compute(ResultBuilder<T, AccountInfoDto> result) {
    log.info("Retrieving my Account " + email);

    Specification<Account> myName = new AccountByEmailSpec(email, repository);
    var myAccount = repository.queryFirst(myName);

    if (myAccount.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot retrieve my Account")
              .instance()
              .build());
    }

    var dto =
        new ReflectionMapper<Account, AccountInfoDto>(AccountInfoDto.class).map(myAccount.get());
    return result.success(dto);
  }
}
