package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientSeatsExceptionMapper implements ExceptionMapper<InsufficientSeatsException> {

    private final String ERROR_CODE = "INSUFFICIENT_SEATS";
    private final String ERROR_MESSAGE = "There are currently no available seats. Please come back later.";

    @Override
    public Response toResponse(InsufficientSeatsException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
