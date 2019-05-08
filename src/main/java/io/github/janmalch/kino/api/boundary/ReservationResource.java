package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.reservation.*;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("reservation")
@Api
public class ReservationResource {

  @Path("")
  @POST
  @Secured
  @RolesAllowed("CUSTOMER")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "", response = Long.class)
  public Response newReservation(
      @Context SecurityContext securityContext, ReservationDto reservationDto) {
    var control =
        new NewReservationControl(securityContext.getUserPrincipal().getName(), reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("my-reservations")
  @GET
  @Secured
  @RolesAllowed("CUSTOMER")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMyReservations(@Context SecurityContext securityContext) {
    var control = new GetMyReservationsControl(securityContext.getUserPrincipal().getName());
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("my-reservation/{id}")
  @PUT
  @Secured
  @RolesAllowed("CUSTOMER")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "")
  public Response updateMyReservation(
      @Context SecurityContext securityContext,
      @PathParam("id") long id,
      ReservationDto reservationDto) {
    var control =
        new UpdateMyReservationControl(
            securityContext.getUserPrincipal().getName(), id, reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @GET
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReservationById(@PathParam("id") long id) {
    var control = new GetReservationByIdControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("")
  @GET
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllReservations() {
    var control = new GetAllReservationsControl();
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @PUT
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "")
  public Response updateReservationById(@PathParam("id") long id, ReservationDto reservationDto) {
    var control = new UpdateReservationByIdControl(id, reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }
}
