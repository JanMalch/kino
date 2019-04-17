package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.LoginDto;
import io.github.janmalch.kino.api.model.TokenDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.LogInControl;
import io.github.janmalch.kino.security.Token;
import io.github.janmalch.kino.success.Success;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("login")
@Api
public class LoginResource {

  // TODO: refactor to @Inject
  private Logger log = LoggerFactory.getLogger(LoginResource.class);

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

  static class JwtResultBuilder extends ResponseResultBuilder<Token> {

    @Override
    public Response success(Token payload) {
      var dto = new TokenDto();
      dto.setToken(payload.getTokenString());
      return Response.ok(Success.valueOf(dto)).build();
    }
  }
}
