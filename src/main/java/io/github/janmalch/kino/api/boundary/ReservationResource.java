package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.SuccessMessage;
import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.api.model.ReservationInfoDto;
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
  @ApiOperation(value = "Returns id of created Reservation", response = Long.class)
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
  @ApiOperation(
      value = "Returns users reservations ",
      response = ReservationInfoDto.class,
      responseContainer = "List")
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
  @ApiOperation(value = "Updates users reservation for given ID", response = SuccessMessage.class)
  public Response updateMyReservation(
      @Context SecurityContext securityContext,
      @PathParam("id") long id,
      ReservationDto reservationDto) {
    var control =
        new UpdateMyReservationControl(
            securityContext.getUserPrincipal().getName(), id, reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("my-reservation/{id}")
  @DELETE
  @Secured
  @RolesAllowed("CUSTOMER")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes users reservation for given ID", response = SuccessMessage.class)
  public Response deleteMyReservationById(
      @PathParam("id") long id, @Context SecurityContext securityContext) {
    var control =
        new DeleteMyReservationByIdControl(id, securityContext.getUserPrincipal().getName());
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @GET
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Returns reservation for given ID", response = ReservationInfoDto.class)
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
  @ApiOperation(
      value = "Returns all reservations",
      response = ReservationInfoDto.class,
      responseContainer = "List")
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
  @ApiOperation(value = "Updates reservation for given ID", response = SuccessMessage.class)
  public Response updateReservationById(@PathParam("id") long id, ReservationDto reservationDto) {
    var control = new UpdateReservationByIdControl(id, reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }

  @Path("{id}")
  @DELETE
  @Secured
  @RolesAllowed("MODERATOR")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Deletes reservation for given ID", response = SuccessMessage.class)
  public Response deleteReservationById(@PathParam("id") long id) {
    var control = new DeleteReservationByIdControl(id);
    return control.execute(new ResponseResultBuilder<>());
  }
}
