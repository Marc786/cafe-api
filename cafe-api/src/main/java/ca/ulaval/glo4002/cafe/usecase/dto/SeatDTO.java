package ca.ulaval.glo4002.cafe.usecase.dto;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;

import java.util.Objects;

public class SeatDTO {

    private final SeatId id;
    private final Status status;
    private final CustomerId customerId;
    private String groupName;

    public SeatDTO(SeatId seatId, Status status, CustomerId customerId, String groupName) {
        this.id = seatId;
        this.status = status;
        this.customerId = customerId;
        this.groupName = groupName;
    }

    public SeatDTO(SeatId id, Status status, CustomerId customerId) {
        this.id = id;
        this.status = status;
        this.customerId = customerId;
    }

    public SeatId getSeatId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SeatDTO seatDTO = (SeatDTO) o;
        return Objects.equals(id, seatDTO.id) && status == seatDTO.status && Objects.equals(customerId,
            seatDTO.customerId) && Objects.equals(groupName, seatDTO.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, customerId, groupName);
    }
}
