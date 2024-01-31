package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;

public class CustomerDTOAssembler {

    public CustomerDTO toCustomerDTOWithGroup(String name, SeatId seatId, String group) {
        return new CustomerDTO(name, seatId, group);
    }
}
