package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.account.AccountInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.ReflectionMapper;
import java.util.ArrayList;
import java.util.List;

/** Get all Accounts as List from database */
public class GetAllAccountsControl extends ManagingControl<List<AccountInfoDto>> {
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private final List<AccountInfoDto> accountList = new ArrayList<>();

  @Override
  public <T> T compute(ResultBuilder<T, List<AccountInfoDto>> result) {
    manage(repository);
    var repositoryList = repository.findAll();
    var mapper = new ReflectionMapper<Account, AccountInfoDto>(AccountInfoDto.class);

    for (Account acc : repositoryList) {
      var dto = mapper.map(acc);
      accountList.add(dto);
    }
    return result.success(accountList);
  }
}
