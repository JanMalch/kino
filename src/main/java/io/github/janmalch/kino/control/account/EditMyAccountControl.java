package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.repository.specification.UserByEmailSpec;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.util.Mapper;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditMyAccountControl implements Control<Account> {

  private Logger log = LoggerFactory.getLogger(EditMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);

  private final Token token;
  private final AccountDto data;

  public EditMyAccountControl(Token token, AccountDto data) {
    this.token = token;
    this.data = data;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Account> result) {
    log.info("Editing my Account " + token.getName());
    Specification<Account> myName = new UserByEmailSpec(token.getName());
    var myAccount = repository.queryFirst(myName);

    if (myAccount.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot retrieve my Account")
              .instance()
              .build());
    }

    var mapper = new UpdateMyAccountMapper();
    var entity = mapper.updateEntity(data, myAccount.get());
    repository.update(entity);
    return result.success(entity, "My Account was successfully updated");
  }

  static class UpdateMyAccountMapper implements Mapper<Account, AccountDto> {
    private final PasswordManager pm = new PasswordManager();

    public Account updateEntity(AccountDto partialUpdate, Account existingEntity) {
      if (partialUpdate.getEmail() != null) {
        existingEntity.setEmail(partialUpdate.getEmail());
      }
      if (partialUpdate.getFirstName() != null) {
        existingEntity.setFirstName(partialUpdate.getFirstName());
      }
      if (partialUpdate.getLastName() != null) {
        existingEntity.setLastName(partialUpdate.getLastName());
      }
      if (partialUpdate.getBirthday() != null) {
        existingEntity.setBirthday(partialUpdate.getBirthday());
      }
      if (partialUpdate.getPassword() != null) {
        var salt = existingEntity.getSalt();
        var hashedPw = pm.hashPassword(partialUpdate.getPassword(), salt);
        existingEntity.setHashedPassword(hashedPw);
      }
      return existingEntity;
    }
  }
}
