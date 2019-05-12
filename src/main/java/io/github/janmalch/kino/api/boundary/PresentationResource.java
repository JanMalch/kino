package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.PresentationWithSeatsDto;
import io.github.janmalch.kino.control.generic.GetEntityControl;
import io.github.janmalch.kino.control.presentation.PresentationWithSeatsDtoMapper;
import io.github.janmalch.kino.entity.Presentation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api
@Path("presentation")
public class PresentationResource {

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Returns presentation for given ID",
      response = PresentationWithSeatsDto.class)
  public Response getPresentation(@PathParam("id") long id) {
    var control =
        new GetEntityControl<>(id, Presentation.class, new PresentationWithSeatsDtoMapper());
    return control.execute(new ResponseResultBuilder<>());
  }
}
