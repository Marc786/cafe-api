package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidGroupReservationMethodException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InvalidGroupReservationMethodExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_GROUP_RESERVATION_METHOD";
    private static final String ERROR_MESSAGE = "The group reservation method is not supported.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InvalidGroupReservationMethodExceptionMapper invalidGroupReservationMethodExceptionMapper = new InvalidGroupReservationMethodExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = invalidGroupReservationMethodExceptionMapper.toResponse(
            new InvalidGroupReservationMethodException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
