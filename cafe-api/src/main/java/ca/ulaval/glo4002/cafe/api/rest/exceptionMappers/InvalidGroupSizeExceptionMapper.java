package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.InvalidGroupSizeException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupSizeExceptionMapper implements ExceptionMapper<InvalidGroupSizeException> {

    private final String ERROR_CODE = "INVALID_GROUP_SIZE";
    private final String ERROR_MESSAGE = "Groups must reserve at least two seats.";

    @Override
    public Response toResponse(InvalidGroupSizeException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
