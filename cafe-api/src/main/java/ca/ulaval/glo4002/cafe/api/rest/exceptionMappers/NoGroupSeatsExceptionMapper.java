package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoGroupSeatsException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoGroupSeatsExceptionMapper implements ExceptionMapper<NoGroupSeatsException> {

    private final String ERROR_CODE = "NO_GROUP_SEATS";
    private final String ERROR_MESSAGE = "There are no more seats reserved for that group.";

    @Override
    public Response toResponse(NoGroupSeatsException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
