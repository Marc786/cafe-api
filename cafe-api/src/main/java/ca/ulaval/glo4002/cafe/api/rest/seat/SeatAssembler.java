package ca.ulaval.glo4002.cafe.api.rest.seat;

import static java.util.Objects.isNull;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.usecase.dto.SeatDTO;

import java.util.List;
import java.util.stream.Collectors;

public class SeatAssembler {

    public List<SeatResponse> toSeatsResponse(List<SeatDTO> seats) {
        return seats.stream().map(this::createSeatResponse).collect(Collectors.toList());
    }

    private SeatResponse createSeatResponse(SeatDTO seat) {
        return new SeatResponse(seat.getSeatId().getValue(), seat.getStatus().status,
            displayCustomerId(seat.getCustomerId()),
            seat.getGroupName());
    }

    private String displayCustomerId(CustomerId customerId) {
        if (isNull(customerId))
            return null;
        else
            return customerId.getValue();
    }
}
