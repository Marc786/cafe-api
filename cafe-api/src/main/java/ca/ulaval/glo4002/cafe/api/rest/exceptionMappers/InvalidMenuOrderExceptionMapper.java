package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InvalidMenuOrderException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidMenuOrderExceptionMapper implements ExceptionMapper<InvalidMenuOrderException> {

    private final String ERROR_CODE = "INVALID_MENU_ORDER";
    private final String ERROR_MESSAGE = "An item ordered is not on the menu.";

    @Override
    public Response toResponse(InvalidMenuOrderException e) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity(new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE))
            .build();
    }
}
