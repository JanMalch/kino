package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.AccountDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.control.account.DeleteMyAccountControl;
import io.github.janmalch.kino.control.account.EditMyAccountControl;
import io.github.janmalch.kino.control.account.GetMyAccountControl;
import io.github.janmalch.kino.control.account.SignUpControl;
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

@Path("user")
@Api
public class UserResource {

  // TODO: refactor to @Inject
  private Logger log = LoggerFactory.getLogger(UserResource.class);

  @POST
  @Path("sign-up")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response signUp(AccountDto data) {
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
  @ApiOperation(value = "Returns own profile", response = AccountDto.class)
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
  @ApiOperation(value = "Updates own profile", response = AccountDto.class)
  public Response editMyAccount(AccountDto data, @Context SecurityContext securityContext) {
    log.info("------------------ BEGIN EDIT MY-ACCOUNT REQUEST ------------------");
    log.info(data.toString());
    var myAccountToken = securityContext.getUserPrincipal();

    EditMyAccountControl control = new EditMyAccountControl(myAccountToken.getName(), data);

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
    log.info("------------------ BEGIN EDIT MY-ACCOUNT REQUEST ------------------");
    var myAccountToken = securityContext.getUserPrincipal();

    DeleteMyAccountControl control = new DeleteMyAccountControl((Token) myAccountToken);

    return control.execute(new AuthResource.JwtResultBuilder());
  }
}
