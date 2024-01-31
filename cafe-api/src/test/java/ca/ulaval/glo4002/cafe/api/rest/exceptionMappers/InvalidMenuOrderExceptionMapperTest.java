package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InvalidMenuOrderException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InvalidMenuOrderExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_MENU_ORDER";
    private static final String ERROR_MESSAGE = "An item ordered is not on the menu.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InvalidMenuOrderExceptionMapper invalidMenuOrderExceptionMapper = new InvalidMenuOrderExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = invalidMenuOrderExceptionMapper.toResponse(new InvalidMenuOrderException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
