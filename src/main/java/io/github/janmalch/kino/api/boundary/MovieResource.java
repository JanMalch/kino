package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.MovieDto;
import io.github.janmalch.kino.control.*;
import io.github.janmalch.kino.control.movie.GetMovieControl;
import io.github.janmalch.kino.control.movie.NewMovieControl;
import io.github.janmalch.kino.control.movie.RemoveMovieControl;
import io.github.janmalch.kino.control.movie.UpdateMovieControl;
import io.github.janmalch.kino.entity.Movie;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("movie")
@Api
public class MovieResource {

  // TODO: refactor to @Inject
  private Logger log = LoggerFactory.getLogger(MovieResource.class);

  @Path("")
  @POST
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Returns the ID for the newly created movie", response = Long.class)
  public Response newMovie(MovieDto movieDto) {
    log.info(movieDto.toString());
    Control<Long> control = new NewMovieControl(movieDto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @DELETE
  @Secured
  @RolesAllowed("MODERATOR")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes the movie for the given ID", response = Object.class)
  public Response deleteMovie(@PathParam("id") long id) {
    var control = new RemoveMovieControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Retrieves the movie for the given ID", response = Movie.class)
  public Response getMovie(@PathParam("id") long id) {
    var control = new GetMovieControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @PUT
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Partially updates the movie for the given ID", response = Object.class)
  public Response updateMovie(MovieDto movieDto, @PathParam("id") long id) {
    var control = new UpdateMovieControl(movieDto, id);
    return control.execute(new ResponseResultBuilder<>());
  }
}
