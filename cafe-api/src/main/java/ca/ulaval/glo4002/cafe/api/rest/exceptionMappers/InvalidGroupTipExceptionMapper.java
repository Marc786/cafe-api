package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.tax.exception.InvalidGroupTipException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidGroupTipExceptionMapper implements ExceptionMapper<InvalidGroupTipException> {

    private final String ERROR_CODE = "INVALID_GROUP_TIP_RATE";
    private final String ERROR_MESSAGE = "The group tip rate must be set to a value between 0 to 100.";

    @Override
    public Response toResponse(InvalidGroupTipException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
