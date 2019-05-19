package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.control.account.*;
import io.github.janmalch.kino.security.Secured;
import io.github.janmalch.kino.security.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("account")
@Api
public class AccountResource {

  // TODO: refactor to @Inject
  private Logger log = LoggerFactory.getLogger(AccountResource.class);

  @POST
  @Path("sign-up")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(SignUpDto data) {
    log.info("------------------ BEGIN SIGN UP REQUEST ------------------");
    log.info(data.toString());
    var control = new SignUpControl(data);
    Response response = control.execute(new ResponseResultBuilder<>());
    log.info("sending response\n\t" + response.getEntity());
    return response;
  }

  @Path("my-account")
  @Secured
  @RolesAllowed("CUSTOMER")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Returns own profile", response = AccountInfoDto.class)
  public Response getMyAccount(@Context SecurityContext securityContext) {
    log.info("------------------ BEGIN GET MY-ACCOUNT REQUEST ------------------");

    var myAccountToken = securityContext.getUserPrincipal();

    GetMyAccountControl control = new GetMyAccountControl(myAccountToken.getName());

    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("my-account")
  @Secured
  @RolesAllowed("CUSTOMER")
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Updates own profile", response = TokenDto.class)
  public Response editMyAccount(SignUpDto data, @Context SecurityContext securityContext) {
    log.info("------------------ BEGIN EDIT MY-ACCOUNT REQUEST ------------------");
    log.info(data.toString());
    var myAccountToken = securityContext.getUserPrincipal();

    EditMyAccountControl control = new EditMyAccountControl((Token) myAccountToken, data);

    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("my-account")
  @Secured
  @RolesAllowed("CUSTOMER")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Deletes own profile and returns an invalid Cookie",
      response = TokenDto.class)
  public Response deleteMyAccount(@Context SecurityContext securityContext) {
    log.info("------------------ BEGIN DELETE MY-ACCOUNT REQUEST ------------------");
    var myAccountToken = securityContext.getUserPrincipal();

    DeleteMyAccountControl control = new DeleteMyAccountControl((Token) myAccountToken);

    return control.execute(new AuthResource.JwtResultBuilder());
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

  @Path("")
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
