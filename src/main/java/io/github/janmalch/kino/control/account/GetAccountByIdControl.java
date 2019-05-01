package io.github.janmalch.kino.control.account;

import static io.github.janmalch.kino.util.functions.FunctionUtils.mapToDto;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;

public class GetAccountByIdControl implements Control<AccountInfoDto> {

  private final long id;
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  public GetAccountByIdControl(long id) {
    this.id = id;
  }

  @Override
  public <T> T execute(ResultBuilder<T, AccountInfoDto> result) {
    var account = repository.find(id);

    var data = mapToDto(account);
    return result.success(data);
  }
}
