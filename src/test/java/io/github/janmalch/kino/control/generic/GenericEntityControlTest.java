package io.github.janmalch.kino.control.generic;

import static org.junit.jupiter.api.Assertions.*;

import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.entity.Account;
import io.github.janmalch.kino.entity.EntityWiper;
import io.github.janmalch.kino.util.either.EitherResultBuilder;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericEntityControlTest {

  @BeforeEach
  void setup() throws IOException, ClassNotFoundException {
    new EntityWiper().deleteAll();
  }

  @Test
  void execute() {
    // new
    var dto = new AccountDto();
    dto.setEmail("test@example.com");
    var newEntityControl = new NewEntityControl<>(dto, Account.class);
    var newResult = newEntityControl.execute(new EitherResultBuilder<>());
    assertTrue(newResult.isSuccess());
    var id = newResult.getSuccess().getData();

    // update
    var updateDto = new AccountDto();
    updateDto.setFirstName("Jan");
    var updateEntityControl = new UpdateEntityControl<>(id, updateDto, Account.class);
    var updateResult = updateEntityControl.execute(new EitherResultBuilder<>());
    assertTrue(updateResult.isSuccess());

    // get
    var getEntityControl = new GetEntityControl<>(id, Account.class, AccountDto.class);
    var getResult = getEntityControl.execute(new EitherResultBuilder<>());
    assertTrue(getResult.isSuccess());
    assertNotNull(getResult.getSuccess().getData());
    assertTrue(getResult.getSuccess().getData().toString().contains("Jan"));

    // get all
    var getAllEntitiesControl = new GetEntitiesControl<>(Account.class, AccountDto.class);
    var getAllResult = getAllEntitiesControl.execute(new EitherResultBuilder<>());
    assertTrue(getAllResult.isSuccess());
    assertEquals(1, getAllResult.getSuccess().getData().size());

    // delete
    var deleteEntityControl = new DeleteEntityControl<>(id, Account.class);
    var deleteResult = deleteEntityControl.execute(new EitherResultBuilder<>());
    assertTrue(deleteResult.isSuccess());

    // get all again
    var getAllAgainResult = getAllEntitiesControl.execute(new EitherResultBuilder<>());
    assertTrue(getAllAgainResult.isSuccess());
    assertEquals(0, getAllAgainResult.getSuccess().getData().size());
  }
}
