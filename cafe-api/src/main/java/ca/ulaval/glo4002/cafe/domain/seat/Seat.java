package ca.ulaval.glo4002.cafe.domain.seat;

import static java.util.Objects.isNull;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import java.util.Objects;

public class Seat {

    private final SeatId seatId;
    private Status status;
    private CustomerId customerId;
    private String groupName;

    public Seat(SeatId id, Status status) {
        this.seatId = id;
        this.status = status;
    }

    public Seat(SeatId id, Status status, CustomerId customerId, String groupName) {
        this.seatId = id;
        this.status = status;
        this.customerId = customerId;
        this.groupName = groupName;
    }

    public SeatId getSeatId() {
        return seatId;
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

    public boolean assignCustomerIfPossible(CustomerId customerId) {
        if (isAvailable()) {
            assignCustomer(customerId);
            return true;
        }
        return false;
    }

    public boolean assignCustomerToGroupIfPossible(CustomerId customerId, String groupName) {
        if (isReserved() && groupName.equals(this.groupName)) {
            assignCustomer(customerId);
            return true;
        }
        return false;
    }

    public void assignCustomer(CustomerId customerId) {
        this.customerId = customerId;
        setOccupied();
    }

    public void checkoutCustomer() {
        unassignCustomer();
    }

    public void unassignCustomer() {
        customerId = null;
        groupName = null;
        setAvailable();
    }

    public boolean isAvailable() {
        return Status.AVAILABLE.equals(status);
    }

    public boolean isReserved() {
        return Status.RESERVED.equals(status);
    }


    public boolean isInGroupWithGroupName(String groupName) {
        return !isNull(groupName) && groupName.equals(this.groupName);
    }

    public boolean equals(Seat seat) {
        return seatId == seat.getSeatId() && status.equals(seat.status);
    }

    private void setAvailable() {
        status = Status.AVAILABLE;
    }

    private void setOccupied() {
        status = Status.OCCUPIED;
    }

    public void reserve(String groupName) {
        status = Status.RESERVED;
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Seat that = (Seat) o;
        return Objects.equals(seatId, that.seatId) && status == that.status && Objects.equals(customerId,
            that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, status, customerId);
    }
}
