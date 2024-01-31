package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.customer.exception.DuplicateCustomerIdException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class DuplicateCustomerIdExceptionMapperTest {

    private static final String ERROR_CODE = "DUPLICATE_CUSTOMER_ID";
    private static final String ERROR_MESSAGE = "The customer cannot visit the café multiple times in the same day.";

    @Test
    void toResponse_returnsExpectedResponse() {
        DuplicateCustomerIdExceptionMapper duplicateCustomerIdExceptionMapper = new DuplicateCustomerIdExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = duplicateCustomerIdExceptionMapper.toResponse(new DuplicateCustomerIdException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
