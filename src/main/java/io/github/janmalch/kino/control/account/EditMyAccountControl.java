package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.RepositoryFactory;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.util.Mapper;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditMyAccountControl implements Control<Token> {

  private Logger log = LoggerFactory.getLogger(EditMyAccountControl.class);
  private final Repository<Account> repository = RepositoryFactory.createRepository(Account.class);
  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();

  private final Token token;
  private final SignUpDto data;

  public EditMyAccountControl(Token token, SignUpDto data) {
    this.token = token;
    this.data = data;
  }

  @Override
  public <T> T execute(ResultBuilder<T, Token> result) {
    log.info("Editing my Account " + token.getName());
    Specification<Account> myName = new AccountByEmailSpec(token.getName());
    var myAccount = repository.queryFirst(myName);

    if (myAccount.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot retrieve my Account")
              .instance()
              .build());
    }

    var mapper = new UpdateAccountMapper();
    var entity = mapper.updateEntity(data, myAccount.get());
    if (!data.getEmail().equals(token.getName())) {
      repository.update(entity);
      blacklist.addToBlackList(token);
      return result.success(
          factory.generateToken(data.getEmail()), "My Account was successfully updated");
    }
    return result.success(null, "My Account was successfully updated");
  }

  public static class UpdateAccountMapper implements Mapper<Account, SignUpDto> {
    private final PasswordManager pm = new PasswordManager();

    Account updateEntity(SignUpDto partialUpdate, Account existingEntity) {
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
