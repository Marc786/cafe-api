package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoReservationsFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoReservationsFoundExceptionMapper implements ExceptionMapper<NoReservationsFoundException> {

    private final String ERROR_CODE = "NO_RESERVATIONS_FOUND";
    private final String ERROR_MESSAGE = "No reservations were made today for that group.";

    @Override
    public Response toResponse(NoReservationsFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
