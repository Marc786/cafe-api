package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvalidCountryExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_COUNTRY";
    private static final String ERROR_MESSAGE = "The specified country is invalid.";
    InvalidCountryExceptionMapper insufficientSeatsExceptionMapper;

    @BeforeEach
    void createMapper() {
        insufficientSeatsExceptionMapper = new InvalidCountryExceptionMapper();
    }

    @Test
    void toResponse_returnsExpectedResponse() {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = insufficientSeatsExceptionMapper.toResponse(new InvalidCountryException());

        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
        assertThat(response.getStatus()).isEqualTo(400);
    }
}
