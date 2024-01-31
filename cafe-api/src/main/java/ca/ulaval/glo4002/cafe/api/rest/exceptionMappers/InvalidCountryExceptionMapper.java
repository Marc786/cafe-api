package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidCountryExceptionMapper implements ExceptionMapper<InvalidCountryException> {

    private final String ERROR_CODE = "INVALID_COUNTRY";
    private final String ERROR_MESSAGE = "The specified country is invalid.";

    @Override
    public Response toResponse(InvalidCountryException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
