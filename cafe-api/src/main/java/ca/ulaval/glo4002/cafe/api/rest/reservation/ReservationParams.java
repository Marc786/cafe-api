package ca.ulaval.glo4002.cafe.api.rest.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.InvalidGroupSizeException;

public class ReservationParams {

    private final String groupName;
    private final int groupSize;

    public ReservationParams(String groupName, int groupSize) {
        this.groupName = groupName;
        this.groupSize = validateGroupSizeOver1(groupSize);
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupSize() {
        return groupSize;
    }

    private int validateGroupSizeOver1(int groupSize) {
        if (groupSize < 2)
            throw new InvalidGroupSizeException();

        return groupSize;
    }
}
