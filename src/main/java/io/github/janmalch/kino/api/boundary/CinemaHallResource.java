package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.CinemaHallDto;
import io.github.janmalch.kino.control.cinemahall.CinemaHallDtoMapper;
import io.github.janmalch.kino.control.generic.GetEntitiesControl;
import io.github.janmalch.kino.entity.CinemaHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
