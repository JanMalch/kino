package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.util.Mapper;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditAccountById implements Control<Void> {
  private Logger log = LoggerFactory.getLogger(EditMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final AccountDto dto;

  public EditAccountById(AccountDto dto) {
    this.dto = dto;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Void> result) {
    log.info("Editing Account ID: " + dto.getId());
    var repoAcc = repository.find(dto.getId());
    if (repoAcc == null) {
      return result.failure(
          Problem.builder(Response.Status.NOT_FOUND)
              .type("Cannot delete Account")
              .instance()
              .build());
    }

    var mapper = new UpdateAccountMapper();
    var updatedAccount = mapper.updateMapper(dto, repoAcc);
    repository.update(updatedAccount);
    return result.success(null, "Account was successfully updated");
  }

  static class UpdateAccountMapper implements Mapper<Account, AccountDto> {
    private final EditMyAccountControl.UpdateAccountMapper ua =
        new EditMyAccountControl.UpdateAccountMapper();

    public Account updateMapper(AccountDto partialDto, Account entity) {

      ua.updateEntity(partialDto, entity);

      if (partialDto.getRole() != null) {
        entity.setRole(partialDto.getRole());
      }
      return entity;
    }
  }
}
