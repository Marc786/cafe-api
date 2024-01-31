package ca.ulaval.glo4002.cafe.api.rest.customer;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CustomerResponse;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;

import org.junit.jupiter.api.Test;

class CustomerAssemblerTest {

    private final String CUSTOMER_NAME = "jean-claude";
    private final String CUSTOMER_ID = "99";
    private final SeatId SEAT_ID = new SeatId(5);

    @Test
    void customer_convertToCustomerResponse_returnsExpectedCustomerResponse() {
        CustomerAssembler customerAssembler = new CustomerAssembler();
        CustomerDTO customer = new CustomerDTO(CUSTOMER_NAME, SEAT_ID, CUSTOMER_ID);
        CustomerResponse expectedCustomerResponse = new CustomerResponse(CUSTOMER_NAME, SEAT_ID.getValue(),
            CUSTOMER_ID);

        CustomerResponse customerResponse = customerAssembler.toCustomerResponse(customer);

        assertThat(customerResponse).isEqualTo(expectedCustomerResponse);
    }
}
