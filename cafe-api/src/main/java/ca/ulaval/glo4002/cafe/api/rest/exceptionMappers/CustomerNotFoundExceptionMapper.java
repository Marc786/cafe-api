package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    private final String ERROR_CODE = "INVALID_CUSTOMER_ID";
    private final String ERROR_MESSAGE = "The customer does not exist.";

    @Override
    public Response toResponse(CustomerNotFoundException e) {
        return Response.status(Status.NOT_FOUND)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
