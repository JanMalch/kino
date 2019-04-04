package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.SignUpDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.SignUpControl;
import io.swagger.annotations.Api;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
  public Response signUp(SignUpDto data) {
    log.info("------------------ BEGIN SIGN UP REQUEST ------------------");
    log.info(data.toString());
    Control<Object> control = new SignUpControl(data);
    Response response = control.execute(new ResponseResultBuilder<>());
    log.info("sending response\n\t" + response.getEntity());
    return response;
  }
}
