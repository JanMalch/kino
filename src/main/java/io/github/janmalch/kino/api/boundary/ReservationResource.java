package io.github.janmalch.kino.api.boundary;

import io.github.janmalch.kino.api.ResponseResultBuilder;
import io.github.janmalch.kino.api.model.ReservationDto;
import io.github.janmalch.kino.control.Control;
import io.github.janmalch.kino.control.reservation.NewReservationControl;
import io.github.janmalch.kino.security.Secured;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    Control<Long> control =
        new NewReservationControl(securityContext.getUserPrincipal().getName(), reservationDto);
    return control.execute(new ResponseResultBuilder<>());
  }
}
