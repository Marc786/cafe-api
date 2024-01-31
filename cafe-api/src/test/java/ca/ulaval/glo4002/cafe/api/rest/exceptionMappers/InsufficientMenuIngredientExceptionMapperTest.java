package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.inventory.exception.InsufficientIngredientException;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;


class InsufficientMenuIngredientExceptionMapperTest {

    private static final String ERROR_CODE = "INSUFFICIENT_INGREDIENTS";
    private static final String ERROR_MESSAGE = "We lack the necessary number of ingredients to fulfill your order.";

    @Test
    void toResponse_returnsExpectedResponse() {
        InsufficientIngredientExceptionMapper insufficientIngredientExceptionMapper = new InsufficientIngredientExceptionMapper();
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_CODE, ERROR_MESSAGE);

        Response response = insufficientIngredientExceptionMapper.toResponse(new InsufficientIngredientException());

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getEntity()).isEqualTo(exceptionResponse);
    }
}
