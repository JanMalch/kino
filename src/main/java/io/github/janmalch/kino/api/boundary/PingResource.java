package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.model.PingDto;
import io.swagger.annotations.Api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ping")
@Api
public class PingResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PingDto ping() {
    return new PingDto();
  }
}
