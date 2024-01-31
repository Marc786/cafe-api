package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.tax.exception.NoBillException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoBillExceptionMapper implements ExceptionMapper<NoBillException> {

    private final String ERROR_CODE = "NO_BILL";
    private final String ERROR_MESSAGE = "The customer needs to do a checkout before receiving his bill.";

    @Override
    public Response toResponse(NoBillException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
