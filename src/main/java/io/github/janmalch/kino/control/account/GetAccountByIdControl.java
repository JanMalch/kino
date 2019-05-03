package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;

public class GetAccountByIdControl implements Control<AccountInfoDto> {

  private final long id;
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  public GetAccountByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, AccountInfoDto> result) {
    var account = Problems.requireEntity(repository.find(id), id, "No account found");

    var mapper = new ReflectionMapper<Account, AccountInfoDto>();
    var data = mapper.map(account, AccountInfoDto.class);
    return result.success(data);
  }
}
