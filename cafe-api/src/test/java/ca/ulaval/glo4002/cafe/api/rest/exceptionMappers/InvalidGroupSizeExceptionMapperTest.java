package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.InvalidGroupSizeException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InvalidGroupSizeExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_GROUP_SIZE";
    private static final String ERROR_MESSAGE = "Groups must reserve at least two seats.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InvalidGroupSizeExceptionMapper invalidGroupSizeExceptionMapper = new InvalidGroupSizeExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = invalidGroupSizeExceptionMapper.toResponse(new InvalidGroupSizeException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
