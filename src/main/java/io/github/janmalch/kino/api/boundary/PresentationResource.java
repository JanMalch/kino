package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.presentation.NewPresentationDto;
import io.github.janmalch.kino.api.model.presentation.PresentationWithSeatsDto;
import io.github.janmalch.kino.control.generic.*;
import io.github.janmalch.kino.control.presentation.NewPresentationDtoMapper;
import io.github.janmalch.kino.control.presentation.PresentationWithSeatsDtoMapper;
import io.github.janmalch.kino.entity.Presentation;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Returns all presentations",
      response = PresentationWithSeatsDto.class,
      responseContainer = "List")
  public Response getAllPresentations() {
    var control =
        new GetEntitiesControl<>(Presentation.class, new PresentationWithSeatsDtoMapper());
    return control.execute(new ResponseResultBuilder<>());
  }

  @POST
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Creates a new presentation", response = Long.class)
  public Response newPresentation(@Valid NewPresentationDto dto) {
    var control = new NewEntityControl<>(dto, Presentation.class, new NewPresentationDtoMapper());
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @PUT
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Updates the presentation for the given ID",
      response = SuccessMessage.class)
  public Response updatePresentation(@Valid NewPresentationDto dto, @PathParam("id") long id) {
    var control =
        new UpdateEntityControl<>(id, dto, Presentation.class, new NewPresentationDtoMapper());
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @DELETE
  @Secured
  @RolesAllowed("MODERATOR")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Deletes the presentation for the given ID",
      response = SuccessMessage.class)
  public Response deletePresentation(@PathParam("id") long id) {
    var control = new DeleteEntityControl<>(id, Presentation.class);
    return control.execute(new ResponseResultBuilder<>());
  }
}
