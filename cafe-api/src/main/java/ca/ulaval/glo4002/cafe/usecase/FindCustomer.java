package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.usecase.assembler.CustomerDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;

public class FindCustomer {

    private final CafeRepository cafeRepository;
    private final CustomerDTOAssembler customerAssembler;

    public FindCustomer(CafeRepository cafeRepository, CustomerDTOAssembler customerAssembler) {
        this.cafeRepository = cafeRepository;
        this.customerAssembler = customerAssembler;
    }

    public CustomerDTO findById(CustomerId id) {
        Cafe cafe = cafeRepository.fetchCafe();
        Customer customer = cafe.findCustomerById(id);
        Seat customerSeat = cafe.findCustomerSeat(id);

        return customerAssembler.toCustomerDTOWithGroup(customer.getName(), customerSeat.getSeatId(),
            customerSeat.getGroupName());
    }
}
