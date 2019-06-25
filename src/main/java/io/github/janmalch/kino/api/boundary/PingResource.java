package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.model.PingDto;
import io.swagger.annotations.Api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("ping")
@Api
public class PingResource {

  private Logger log = LoggerFactory.getLogger(PingResource.class);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public PingDto ping() {
    log.info("new ping received");
    return new PingDto();
  }
}
