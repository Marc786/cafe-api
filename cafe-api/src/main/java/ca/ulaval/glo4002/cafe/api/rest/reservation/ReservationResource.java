package ca.ulaval.glo4002.cafe.api.rest.reservation;

import ca.ulaval.glo4002.cafe.usecase.CreateReservation;
import ca.ulaval.glo4002.cafe.usecase.FindReservation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ReservationResource {

    private final CreateReservation createReservation;
    private final FindReservation findReservation;
    private final ReservationAssembler reservationAssembler;

    public ReservationResource(CreateReservation createReservation, FindReservation findReservation,
        ReservationAssembler reservationAssembler) {
        this.createReservation = createReservation;
        this.findReservation = findReservation;
        this.reservationAssembler = reservationAssembler;
    }

    @POST
    @Path("/reservations")
    public Response addReservation(ReservationRequest reservationRequest) {
        ReservationParams reservationParams = new ReservationParams(reservationRequest.groupName(),
            reservationRequest.groupSize());

        createReservation.create(reservationParams.getGroupName(), reservationParams.getGroupSize());

        return Response.status(200).build();
    }

    @GET
    @Path("/reservations")
    public Response getReservations() {
        List<ReservationResponse> reservationResponses = reservationAssembler.toReservationResponses(
            findReservation.findAllReservations());

        return Response.status(200).entity(reservationResponses).build();
    }
}
