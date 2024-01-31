package ca.ulaval.glo4002.cafe.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    private static final String GROUP_NAME = "mon groupe";
    private static final String OTHER_GROUP_NAME = "other groupe";
    private static final int GROUP_SIZE = 4;
    private Reservation reservation;

    @BeforeEach
    void setup() {
        reservation = new Reservation(GROUP_NAME, GROUP_SIZE);
    }

    @Test
    void aReservation_isExpectedGroupNameEquals_groupNameIsEqual() {
        boolean groupNameIsEqual = reservation.isGroupNameEquals(GROUP_NAME);

        assertTrue(groupNameIsEqual);
    }

    @Test
    void aReservation_isOtherGroupNameEquals_groupNameIsNotEqual() {
        boolean groupNameIsEqual = reservation.isGroupNameEquals(OTHER_GROUP_NAME);

        assertFalse(groupNameIsEqual);
    }
}
