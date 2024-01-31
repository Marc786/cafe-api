package ca.ulaval.glo4002.cafe.api.rest.customer;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CustomerResponse;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;

public class CustomerAssembler {

    public CustomerResponse toCustomerResponse(CustomerDTO customer) {
        return new CustomerResponse(customer.getName(), customer.getSeatId().getValue(), customer.getGroup());
    }
}
