package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.NewMovieControl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("movie")
@Api
public class MovieResource {

  // TODO: refactor to @Inject
  private Logger log = LoggerFactory.getLogger(MovieResource.class);

  @Path("new")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Returns the ID for the newly created movie", response = Long.class)
  public Response logIn(MovieDto movieDto) {
    log.info(movieDto.toString());
    Control<Long> control = new NewMovieControl(movieDto);
    return control.execute(new ResponseResultBuilder<>());
  }
}
