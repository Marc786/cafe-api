package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoReservationsFoundException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class NoReservationsFoundExceptionMapperTest {

    private static final String ERROR_CODE = "NO_RESERVATIONS_FOUND";
    private static final String ERROR_MESSAGE = "No reservations were made today for that group.";

    @Test
    void toResponse_returnsExpectedResponse() {
        NoReservationsFoundExceptionMapper noReservationsFoundExceptionMapper = new NoReservationsFoundExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = noReservationsFoundExceptionMapper.toResponse(new NoReservationsFoundException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
