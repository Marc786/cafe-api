package ca.ulaval.glo4002.cafe.usecase.dto;

import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

public class CustomerDTO {

    private final String name;
    private final SeatId seatId;
    private String group;

    public CustomerDTO(String name, SeatId seatId, String group) {
        this.name = name;
        this.seatId = seatId;
        this.group = group;
    }

    public CustomerDTO(String name, SeatId seatId) {
        this.name = name;
        this.seatId = seatId;
    }

    public String getName() {
        return name;
    }

    public SeatId getSeatId() {
        return seatId;
    }

    public String getGroup() {
        return group;
    }
}
