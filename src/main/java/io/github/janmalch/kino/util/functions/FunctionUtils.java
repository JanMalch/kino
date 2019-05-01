package io.github.janmalch.kino.util.functions;

import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.entity.Account;
import java.util.function.Function;

public class FunctionUtils {

  private FunctionUtils() {}

  public static <T, R, E extends Exception> Function<T, R> uncheck(
      FunctionWithException<T, R, E> function) {
    return input -> {
      try {
        return function.apply(input);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  public static <R, E extends Exception> R uncheck(SupplierWithException<R, E> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <E extends Exception> void uncheck(VoidConsumerWithException<E> consumer) {
    try {
      consumer.execute();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static AccountInfoDto mapToDto(Account account) {
    AccountInfoDto info = new AccountInfoDto();
    info.setId(account.getId());
    if (account.getEmail() != null) info.setEmail(account.getEmail());
    if (account.getRole() != null) info.setRole(account.getRole());
    if (account.getFirstName() != null) info.setFirstName(account.getFirstName());
    if (account.getLastName() != null) info.setLastName(account.getLastName());
    if (account.getBirthday() != null) info.setBirthday(account.getBirthday());
    return info;
  }
}
