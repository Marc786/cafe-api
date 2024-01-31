package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoGroupSeatsException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class NoGroupSeatsExceptionMapperTest {

    private static final String ERROR_CODE = "NO_GROUP_SEATS";
    private static final String ERROR_MESSAGE = "There are no more seats reserved for that group.";

    @Test
    void toResponse_returnsExpectedResponse() {
        NoGroupSeatsExceptionMapper noGroupSeatsExceptionMapper = new NoGroupSeatsExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = noGroupSeatsExceptionMapper.toResponse(new NoGroupSeatsException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
