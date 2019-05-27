package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.CinemaHallDto;
import io.github.janmalch.kino.api.model.cinemahall.NewCinemaHallDto;
import io.github.janmalch.kino.control.cinemahall.CinemaHallDtoMapper;
import io.github.janmalch.kino.control.cinemahall.NewCinemaHallControl;
import io.github.janmalch.kino.control.generic.DeleteEntityControl;
import io.github.janmalch.kino.control.generic.GetEntitiesControl;
import io.github.janmalch.kino.entity.CinemaHall;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("cinema-hall")
public class CinemaHallResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Returns all cinema halls",
      response = CinemaHallDto.class,
      responseContainer = "List")
  public Response getAllCinemaHalls() {
    var control = new GetEntitiesControl<>(CinemaHall.class, new CinemaHallDtoMapper());
    return control.execute(new ResponseResultBuilder<>());
  }

  @POST
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Adds a new price category", response = Long.class)
  public Response createCinemaHall(@Valid NewCinemaHallDto dto) {
    var control = new NewCinemaHallControl(dto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @DELETE
  @Secured
  @RolesAllowed("MODERATOR")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes a single cinema hall", response = SuccessMessage.class)
  public Response deleteCinemaHall(@PathParam("id") long id) {
    var control = new DeleteEntityControl<>(id, CinemaHall.class);
    return control.execute(new ResponseResultBuilder<>());
  }
}
