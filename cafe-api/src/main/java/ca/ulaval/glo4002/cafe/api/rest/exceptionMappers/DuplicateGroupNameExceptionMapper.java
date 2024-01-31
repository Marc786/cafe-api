package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.DuplicateGroupNameException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateGroupNameExceptionMapper implements ExceptionMapper<DuplicateGroupNameException> {

    private final String ERROR_CODE = "DUPLICATE_GROUP_NAME";
    private final String ERROR_MESSAGE = "The specified group already made a reservation today.";

    @Override
    public Response toResponse(DuplicateGroupNameException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
