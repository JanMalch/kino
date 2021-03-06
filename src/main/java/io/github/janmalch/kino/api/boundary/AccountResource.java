package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.account.AccountInfoDto;
import io.github.janmalch.kino.api.model.account.SignUpDto;
import io.github.janmalch.kino.control.account.*;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("account")
@Api
public class AccountResource {

  private Logger log = LoggerFactory.getLogger(AccountResource.class);

  @POST
  @Path("sign-up")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(@Valid SignUpDto data) {
    log.info("------------------ BEGIN SIGN UP REQUEST ------------------");
    log.info(data.toString());
    var control = new SignUpControl(data);
    Response response = control.execute(new ResponseResultBuilder<>());
    log.info("sending response\n\t" + response.getEntity());
    return response;
  }

  @Path("{id}")
  @Secured
  @RolesAllowed("MODERATOR")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Retrieves the account for the given ID", response = AccountInfoDto.class)
  public Response getAccountById(@PathParam("id") long id) {
    log.info("------------------ BEGIN GET ACCOUNT BY ID REQUEST ------------------");
    GetAccountByIdControl control = new GetAccountByIdControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Secured
  @RolesAllowed("MODERATOR")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Retrieves all accounts",
      response = AccountInfoDto.class,
      responseContainer = "List")
  public Response getAllAccounts() {
    log.info("------------------ BEGIN GET ALL ACCOUNTS ------------------");
    GetAllAccountsControl control = new GetAllAccountsControl();
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @Secured
  @RolesAllowed("ADMIN")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes an account", response = SuccessMessage.class)
  public Response deleteAccount(@PathParam("id") long id) {
    log.info("------------------ BEGIN DELETE ACCOUNT BY ID REQUEST ------------------");
    DeleteAccountByIdControl control = new DeleteAccountByIdControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @Secured
  @RolesAllowed("ADMIN")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Edit an account", response = AccountInfoDto.class)
  public Response editAccountById(@PathParam("id") long id, AccountInfoDto data) {
    log.info("------------------ BEGIN EDIT ACCOUNT BY ID REQUEST ------------------");
    EditAccountById control = new EditAccountById(data);
    return control.execute(new ResponseResultBuilder<>());
  }
}
