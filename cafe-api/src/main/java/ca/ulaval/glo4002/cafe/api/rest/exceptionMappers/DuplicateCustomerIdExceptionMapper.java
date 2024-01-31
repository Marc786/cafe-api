package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.customer.exception.DuplicateCustomerIdException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DuplicateCustomerIdExceptionMapper implements ExceptionMapper<DuplicateCustomerIdException> {

    private final String ERROR_CODE = "DUPLICATE_CUSTOMER_ID";
    private final String ERROR_MESSAGE = "The customer cannot visit the café multiple times in the same day.";

    @Override
    public Response toResponse(DuplicateCustomerIdException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
