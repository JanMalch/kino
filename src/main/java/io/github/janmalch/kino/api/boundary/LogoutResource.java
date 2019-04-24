package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.control.LogOutControl;
import io.github.janmalch.kino.security.Secured;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.success.Success;
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

@Path("logout")
@Api
public class LogoutResource {

  private Logger log = LoggerFactory.getLogger(LogoutResource.class);

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
    public Response success(Token payload) {
      var dto = new TokenDto();
      dto.setToken(payload.getTokenString());
      return Response.ok(Success.valueOf(dto)).build();
    }
  }
}
