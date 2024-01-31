package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.infra.dto.InMemorySeat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemorySeatAssembler {

    public List<InMemorySeat> fromSeatsToInMemorySeats(List<Seat> seats) {
        return seats.stream().map(this::createInMemorySeat).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Seat> fromInMemorySeatsToSeats(List<InMemorySeat> inMemorySeats) {
        return inMemorySeats.stream().map(this::createSeat).collect(Collectors.toCollection(ArrayList::new));
    }

    private InMemorySeat createInMemorySeat(Seat seat) {
        return new InMemorySeat(seat.getSeatId().getValue(), seat.getStatus(), seat.getCustomerId(),
            seat.getGroupName());
    }

    private Seat createSeat(InMemorySeat inMemorySeat) {
        return new Seat(new SeatId(inMemorySeat.getNumber()), inMemorySeat.getStatus(),
            inMemorySeat.getCustomerId(), inMemorySeat.getGroupName());
    }
}
