package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.usecase.dto.SeatDTO;

import java.util.List;

public class SeatDTOAssembler {

    public List<SeatDTO> fromSeatToSeatsDTO(List<Seat> seats) {
        return seats.stream().map(this::createSeatDTO).toList();
    }

    private SeatDTO createSeatDTO(Seat seat) {
        return new SeatDTO(seat.getSeatId(), seat.getStatus(), seat.getCustomerId(), seat.getGroupName());
    }
}
