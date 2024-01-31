package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InsufficientSeatsExceptionMapperTest {

    private static final String ERROR_CODE = "INSUFFICIENT_SEATS";
    private static final String ERROR_MESSAGE = "There are currently no available seats. Please come back later.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InsufficientSeatsExceptionMapper insufficientSeatsExceptionMapper = new InsufficientSeatsExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = insufficientSeatsExceptionMapper.toResponse(new InsufficientSeatsException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
