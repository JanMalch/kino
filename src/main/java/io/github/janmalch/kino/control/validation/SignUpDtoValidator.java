package io.github.janmalch.kino.control.validation;

import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.problem.Problem;
import io.github.janmalch.kino.util.Contract;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import javax.ws.rs.core.Response;

public class SignUpDtoValidator implements Validator<SignUpDto> {

  private final SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public Optional<Problem> validate(SignUpDto data) {
    Contract.require(data != null, "SignUp data is null");

    var validator = new BeanValidations<>(data, "sign-up");
    return validator
        .requireNotEmpty(/* check all fields for null/empty */ )
        .or(() -> checkValidBirthday(data));
  }

  Optional<Problem> checkValidBirthday(SignUpDto data) {
    try {
      birthdayFormat.parse(data.getBirthday());
      return Optional.empty();
    } catch (ParseException e) {
      Problem problem =
          Problem.builder()
              .type("sign-up/invalid-date-format")
              .title("Birthday has an invalid format")
              .status(Response.Status.BAD_REQUEST)
              .detail(
                  String.format(
                      "The given birthday '%s' has an invalid format", data.getBirthday()))
              .instance()
              .parameter("birthday", data.getBirthday())
              .build();

      return Optional.of(problem);
    }
  }
}
