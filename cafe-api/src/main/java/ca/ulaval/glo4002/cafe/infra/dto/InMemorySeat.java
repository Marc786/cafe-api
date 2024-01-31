package ca.ulaval.glo4002.cafe.infra.dto;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;

import java.util.Objects;

public class InMemorySeat {

    private final int number;
    private final Status status;
    private final CustomerId customerId;
    private final String groupName;

    public InMemorySeat(int number, Status status, CustomerId customerId, String groupName) {
        this.number = number;
        this.status = status;
        this.customerId = customerId;
        this.groupName = groupName;
    }

    public int getNumber() {
        return number;
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
        InMemorySeat that = (InMemorySeat) o;
        return number == that.number && status == that.status && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, status, customerId);
    }
}
