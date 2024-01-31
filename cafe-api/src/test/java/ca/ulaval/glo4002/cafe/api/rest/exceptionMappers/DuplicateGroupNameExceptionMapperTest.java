package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.DuplicateGroupNameException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class DuplicateGroupNameExceptionMapperTest {

    private static final String ERROR_CODE = "DUPLICATE_GROUP_NAME";
    private static final String ERROR_MESSAGE = "The specified group already made a reservation today.";

    @Test
    void toResponse_returnsExpectedResponse() {
        DuplicateGroupNameExceptionMapper duplicateGroupNameExceptionMapper = new DuplicateGroupNameExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = duplicateGroupNameExceptionMapper.toResponse(new DuplicateGroupNameException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
