package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidGroupReservationMethodException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupReservationMethodExceptionMapper
    implements ExceptionMapper<InvalidGroupReservationMethodException> {

    private final String ERROR_CODE = "INVALID_GROUP_RESERVATION_METHOD";
    private final String ERROR_MESSAGE = "The group reservation method is not supported.";

    @Override
    public Response toResponse(InvalidGroupReservationMethodException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
