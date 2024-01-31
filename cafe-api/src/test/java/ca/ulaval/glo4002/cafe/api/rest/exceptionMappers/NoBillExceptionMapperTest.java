package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.tax.exception.NoBillException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class NoBillExceptionMapperTest {

    private static final String ERROR_CODE = "NO_BILL";
    private static final String ERROR_MESSAGE = "The customer needs to do a checkout before receiving his bill.";

    @Test
    void toResponse_returnsExpectedResponse() {
        NoBillExceptionMapper noBillExceptionMapper = new NoBillExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = noBillExceptionMapper.toResponse(new NoBillException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
