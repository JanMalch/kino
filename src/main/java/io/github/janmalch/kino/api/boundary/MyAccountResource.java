package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.AccountInfoDto;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.control.myaccount.DeleteMyAccountControl;
import io.github.janmalch.kino.control.myaccount.EditMyAccountControl;
import io.github.janmalch.kino.control.myaccount.GetMyAccountControl;
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

@Path("my-account")
@Api
public class MyAccountResource {
  private Logger log = LoggerFactory.getLogger(AccountResource.class);

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
}
