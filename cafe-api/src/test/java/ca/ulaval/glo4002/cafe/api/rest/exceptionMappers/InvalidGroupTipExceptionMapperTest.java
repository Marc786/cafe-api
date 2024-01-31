package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.tax.exception.InvalidGroupTipException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class InvalidGroupTipExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_GROUP_TIP_RATE";
    private static final String ERROR_MESSAGE = "The group tip rate must be set to a value between 0 to 100.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InvalidGroupTipExceptionMapper invalidGroupSizeExceptionMapper = new InvalidGroupTipExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = invalidGroupSizeExceptionMapper.toResponse(new InvalidGroupTipException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
