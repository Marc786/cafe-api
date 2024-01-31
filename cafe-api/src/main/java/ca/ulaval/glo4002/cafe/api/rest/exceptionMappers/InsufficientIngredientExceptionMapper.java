package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.inventory.exception.InsufficientIngredientException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InsufficientIngredientExceptionMapper implements ExceptionMapper<InsufficientIngredientException> {

    private final String ERROR_CODE = "INSUFFICIENT_INGREDIENTS";
    private final String ERROR_MESSAGE = "We lack the necessary number of ingredients to fulfill your order.";

    @Override
    public Response toResponse(InsufficientIngredientException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
