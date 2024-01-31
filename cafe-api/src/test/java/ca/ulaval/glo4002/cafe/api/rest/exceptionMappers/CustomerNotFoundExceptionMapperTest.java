package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class CustomerNotFoundExceptionMapperTest {

    private static final String ERROR_CODE = "INVALID_CUSTOMER_ID";
    private static final String ERROR_MESSAGE = "The customer does not exist.";

    @Test
    void toResponse_returnsExpectedResponse() {
        CustomerNotFoundExceptionMapper customerNotFoundExceptionMapper = new CustomerNotFoundExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = customerNotFoundExceptionMapper.toResponse(new CustomerNotFoundException());

        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
