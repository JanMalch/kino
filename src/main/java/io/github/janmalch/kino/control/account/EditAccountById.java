package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.control.ManagingControl;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.control.myaccount.EditMyAccountControl;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problems;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Edit an account given by AccountInfoDto. */
public class EditAccountById extends ManagingControl<SuccessMessage> {
  private Logger log = LoggerFactory.getLogger(EditMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final AccountInfoDto dto;

  public EditAccountById(AccountInfoDto dto) {
    this.dto = dto;
  }

  @Override
  public <T> T compute(ResultBuilder<T, SuccessMessage> result) {
    manage(repository);
    log.info("Editing Account ID: " + dto.getId());
    var repoAcc =
        Problems.requireEntity(repository.find(dto.getId()), dto.getId(), "No account found");

    var mapper = new UpdateAccountMapper();
    var updatedAccount = mapper.update(dto, repoAcc);
    repository.update(updatedAccount);
    return result.success("Account was successfully updated");
  }

  static class UpdateAccountMapper implements Mapper<AccountInfoDto, Account> {
    private final EditMyAccountControl.UpdateAccountMapper ua =
        new EditMyAccountControl.UpdateAccountMapper();

    @Override
    public Account update(AccountInfoDto update, Account existing) {
      ua.update(update, existing);

      if (update.getRole() != null) {
        existing.setRole(update.getRole());
      }
      return existing;
    }
  }
}
