package ca.ulaval.glo4002.cafe.domain.reservation;

import java.util.Objects;

public class Reservation {

    private final String groupName;
    private final int groupSize;

    public Reservation(String groupName, int groupSize) {
        this.groupName = groupName;
        this.groupSize = groupSize;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public boolean isGroupNameEquals(String groupName) {
        return this.groupName.equals(groupName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reservation that = (Reservation) o;
        return groupSize == that.groupSize && Objects.equals(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, groupSize);
    }
}
