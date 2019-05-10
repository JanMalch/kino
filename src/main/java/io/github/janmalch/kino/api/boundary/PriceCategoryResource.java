package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.PriceCategoryBaseDto;
import io.github.janmalch.kino.api.model.PriceCategoryDto;
import io.github.janmalch.kino.control.generic.*;
import io.github.janmalch.kino.entity.PriceCategory;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("price")
@Api
public class PriceCategoryResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Retrieves all price categories",
      response = PriceCategoryDto.class,
      responseContainer = "List")
  public Response getAllPriceCategories() {
    var control = new GetEntitiesControl<>(PriceCategory.class, PriceCategoryDto.class);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Retrieves all price categories", response = PriceCategoryDto.class)
  public Response getPriceCategory(@PathParam("id") long id) {
    var control = new GetEntityControl<>(id, PriceCategory.class, PriceCategoryDto.class);
    return control.execute(new ResponseResultBuilder<>());
  }

  @POST
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Adds a new price category", response = Long.class)
  public Response createPriceCategory(@Valid PriceCategoryBaseDto dto) {
    var control = new NewEntityControl<>(dto, PriceCategory.class);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @PUT
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Updates the given price category", response = SuccessMessage.class)
  public Response updatePriceCategory(@Valid PriceCategoryDto dto, @PathParam("id") long id) {
    var control = new UpdateEntityControl<>(id, dto, PriceCategory.class);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @DELETE
  @Secured
  @RolesAllowed("MODERATOR")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes the given price category", response = SuccessMessage.class)
  public Response deletePriceCategory(@PathParam("id") long id) {
    var control = new DeleteEntityControl<>(id, PriceCategory.class);
    return control.execute(new ResponseResultBuilder<>());
  }
}
