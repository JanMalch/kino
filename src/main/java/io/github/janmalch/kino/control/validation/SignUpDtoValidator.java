package io.github.janmalch.kino.control.validation;

import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.util.Contract;
import java.util.Optional;

/**
 * @deprecated Use BeanValidations instead
 * @see BeanValidations#requireNotEmpty(String...)
 */
@Deprecated
public class SignUpDtoValidator implements Validator<AccountDto> {

  @Override
  public Optional<Problem> validate(AccountDto data) {
    Contract.require(data != null, "SignUp data is null");

    var validator = new BeanValidations<>(data, "sign-up");
    return validator.requireNotEmpty(/* check all fields for null/empty */ );
  }
}
