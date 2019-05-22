package io.github.janmalch.kino.control.account;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.ResultBuilder;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.repository.Repository;
import io.github.janmalch.kino.repository.specification.AccountByEmailSpec;
import io.github.janmalch.kino.repository.specification.Specification;
import io.github.janmalch.kino.security.JwtTokenBlacklist;
import io.github.janmalch.kino.security.JwtTokenFactory;
import io.github.janmalch.kino.security.PasswordManager;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.util.Mapper;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;

@RequestScoped
public class EditMyAccountControl implements Control<Token> {

  @Inject Logger log;

  @Inject Repository<Account> repository;

  private JwtTokenBlacklist blacklist = JwtTokenBlacklist.getInstance();
  private JwtTokenFactory factory = new JwtTokenFactory();

  private Token token;
  private SignUpDto data;

  public void init(Token token, SignUpDto data) {
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
