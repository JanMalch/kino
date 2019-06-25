package io.github.janmalch.kino.control.myaccount;

import io.github.janmalch.kino.api.model.account.SignUpDto;
import io.github.janmalch.kino.control.ManagingControl;
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

/**
 * When a user wants to edit its own account, it needs a Token and SignUpDto to change Account
 * information. Token is needed for Principal information. SignUpDto is needed to update account
 * information like email, first Name, last Name, Birthday or Password.
 */
public class EditMyAccountControl extends ManagingControl<Token> {

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
  public <T> T compute(ResultBuilder<T, Token> result) {
    manage(repository);
    log.info("Editing my Account " + token.getName());
    Specification<Account> myName = new AccountByEmailSpec(token.getName(), repository);
    var myAccount = repository.queryFirst(myName);

    if (myAccount.isEmpty()) {
      return result.failure(
          Problem.builder(Response.Status.BAD_REQUEST)
              .type("Cannot retrieve my Account")
              .instance()
              .build());
    }

    var mapper = new UpdateAccountMapper();
    var entity = mapper.update(data, myAccount.get());
    repository.update(entity);

    if (!data.getEmail().equals(token.getName())) {
      blacklist.addToBlackList(token);
      return result.success(factory.generateToken(data.getEmail()));
    }
    return result.success(token);
  }

  public static class UpdateAccountMapper implements Mapper<SignUpDto, Account> {
    private final PasswordManager pm = new PasswordManager();

    @Override
    public Account update(SignUpDto update, Account existing) {
      if (update.getEmail() != null) {
        existing.setEmail(update.getEmail());
      }
      if (update.getFirstName() != null) {
        existing.setFirstName(update.getFirstName());
      }
      if (update.getLastName() != null) {
        existing.setLastName(update.getLastName());
      }
      // TODO: fix date issues, the received date is correct
      //  but the stored date is one day earlier
      //  LocalDate apparently is unrelated to a timezone
      if (update.getBirthday() != null) {
        existing.setBirthday(update.getBirthday());
      }
      if (update.getPassword() != null) {
        var salt = existing.getSalt();
        var hashedPw = pm.hashPassword(update.getPassword(), salt);
        existing.setHashedPassword(hashedPw);
      }
      return existing;
    }
  }
}
