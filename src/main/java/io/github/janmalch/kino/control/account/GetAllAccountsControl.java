package io.github.janmalch.kino.control.account;

import static io.github.janmalch.kino.util.functions.FunctionUtils.mapToDto;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import java.util.ArrayList;
import java.util.List;

public class GetAllAccountsControl implements Control<List<AccountInfoDto>> {
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private final List<AccountInfoDto> accountList = new ArrayList<>();

  public GetAllAccountsControl() {}

  @Override
  public <T> T execute(ResultBuilder<T, List<AccountInfoDto>> result) {
    var repositoryList = repository.findAll();

    for (Account acc : repositoryList) {
      accountList.add(mapToDto(acc));
    }
    return result.success(accountList);
  }
}
