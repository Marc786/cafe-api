package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.Objects;

public class InMemoryReservation {

    private final String groupName;
    private final int groupSize;

    public InMemoryReservation(String groupName, int groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryReservation that = (InMemoryReservation) o;
        return groupSize == that.groupSize && Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, groupSize);
    }
}
