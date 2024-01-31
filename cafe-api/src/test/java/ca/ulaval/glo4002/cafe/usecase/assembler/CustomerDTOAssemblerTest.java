package ca.ulaval.glo4002.cafe.usecase.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;

import org.junit.jupiter.api.Test;

class CustomerDTOAssemblerTest {

    private static final String CUSTOMER_NAME = "name";
    private static final SeatId CUSTOMER_SEAT = new SeatId(1);
    private static final String GROUP_NAME = "ungroup";

    @Test
    void customerWithGroup_convertToCustomer_returnExpectedCustomer() {
        CustomerDTOAssembler customerAssembler = new CustomerDTOAssembler();
        CustomerDTO expectedCustomer = new CustomerDTO(CUSTOMER_NAME, CUSTOMER_SEAT, GROUP_NAME);

        CustomerDTO customer = customerAssembler.toCustomerDTOWithGroup(CUSTOMER_NAME, CUSTOMER_SEAT, GROUP_NAME);

        assertThat(customer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(customer.getSeatId()).isEqualTo(expectedCustomer.getSeatId());
        assertThat(customer.getGroup()).isEqualTo(expectedCustomer.getGroup());
    }

    @Test
    void customerWithoutGroup_convertToCustomer_returnExpectedCustomer() {
        CustomerDTOAssembler customerAssembler = new CustomerDTOAssembler();
        CustomerDTO expectedCustomer = new CustomerDTO(CUSTOMER_NAME, CUSTOMER_SEAT);

        CustomerDTO customer = customerAssembler.toCustomerDTOWithGroup(CUSTOMER_NAME, CUSTOMER_SEAT, null);

        assertThat(customer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(customer.getSeatId()).isEqualTo(expectedCustomer.getSeatId());
        assertThat(customer.getGroup()).isEqualTo(null);
    }
}
