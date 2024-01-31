package ca.ulaval.glo4002.cafe.api.rest.exceptionMappers;

import static com.google.common.truth.Truth.assertThat;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class CatchallExceptionMapperTest {

    @Test
    void toResponse_returnsExpectedResponse() {
        CatchallExceptionMapper catchallExceptionMapper = new CatchallExceptionMapper();
        Response response = catchallExceptionMapper.toResponse(new NullPointerException());

        assertThat(response.getStatus()).isEqualTo(400);
    }
}
