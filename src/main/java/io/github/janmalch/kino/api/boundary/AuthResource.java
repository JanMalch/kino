package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.auth.LoginDto;
import io.github.janmalch.kino.api.model.auth.TokenDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.auth.LogInControl;
import io.github.janmalch.kino.control.auth.LogOutControl;
import io.github.janmalch.kino.security.Secured;
import io.github.janmalch.kino.security.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api
@Path("")
public class AuthResource {

  private Logger log = LoggerFactory.getLogger(AuthResource.class);

  @Path("login")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Returns a new JWT token if credentials are valid",
      response = TokenDto.class)
  public Response logIn(LoginDto loginDto) {
    log.info("------------------ BEGIN LOGIN REQUEST ------------------");
    log.info(loginDto.toString());

    Control<Token> control = new LogInControl(loginDto);
    return control.execute(new JwtResultBuilder());
  }

  @Path("logout")
  @Secured
  @RolesAllowed("CUSTOMER")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Returns an invalid JWT token when successfully logged out",
      response = TokenDto.class)
  public Response logOut(@Context SecurityContext securityContext) {
    log.info("------------------ BEGIN LOGOUT REQUEST ------------------");

    var logoutToken = securityContext.getUserPrincipal();

    LogOutControl control = new LogOutControl((Token) logoutToken);

    return control.execute(new JwtResultBuilder());
  }

  static class JwtResultBuilder extends ResponseResultBuilder<Token> {
    @Override
    public Response success(Token payload) {
      var dto = new TokenDto();
      dto.setToken(payload.getTokenString());
      return Response.ok(dto).build();
    }
  }
}
