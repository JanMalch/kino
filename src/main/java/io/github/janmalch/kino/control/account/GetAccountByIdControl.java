package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;

/** Get an account given by ID. */
public class GetAccountByIdControl extends ManagingControl<AccountInfoDto> {

  private final long id;
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  public GetAccountByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T compute(ResultBuilder<T, AccountInfoDto> result) {
    manage(repository);
    var account = Problems.requireEntity(repository.find(id), id, "No account found");

    var mapper = new ReflectionMapper<Account, AccountInfoDto>(AccountInfoDto.class);
    var data = mapper.map(account);
    return result.success(data);
  }
}
