package ca.ulaval.glo4002.cafe.domain.reservation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationListTest {

    private static final String A_GROUP_NAME = "nom";
    private static final int A_GROUP_SIZE = 4;
    private static final String NEW_GROUP_NAME = "notInside";
    private ReservationList reservationList;

    @BeforeEach
    void createReservationList() {
        reservationList = new ReservationList();
    }

    @Test
    void aReservation_addReservation_reservationIsAdded() {
        Reservation reservation = new Reservation(A_GROUP_NAME, A_GROUP_SIZE);

        reservationList.add(reservation);

        assertTrue(reservationList.doesReservationAlreadyExist(A_GROUP_NAME));
    }

    @Test
    void reservationInside_doesReservationExists_returnTrue() {
        reservationList.add(new Reservation(A_GROUP_NAME, A_GROUP_SIZE));

        boolean reservationExist = reservationList.doesReservationAlreadyExist(A_GROUP_NAME);

        assertTrue(reservationExist);
    }

    @Test
    void reservationNotInside_doesReservationExists_returnFalse() {
        reservationList.add(new Reservation(A_GROUP_NAME, A_GROUP_SIZE));

        boolean reservationExist = reservationList.doesReservationAlreadyExist(NEW_GROUP_NAME);

        assertFalse(reservationExist);
    }

    @Test
    void reservationInside_clearReservation_shouldBeEmpty() {
        reservationList.add(new Reservation(A_GROUP_NAME, A_GROUP_SIZE));

        reservationList.clear();

        assertTrue(reservationList.isEmpty());
    }

    @Test
    void reservationInside_removeReservation_reservationIsRemoved() {
        reservationList.add(new Reservation(A_GROUP_NAME, A_GROUP_SIZE));

        reservationList.remove(A_GROUP_NAME);

        assertFalse(reservationList.doesReservationAlreadyExist(A_GROUP_NAME));
    }

    @Test
    void reservationInside_removeNoneExistingReservation_reservationIsNotRemoved() {
        reservationList.add(new Reservation(A_GROUP_NAME, A_GROUP_SIZE));

        reservationList.remove(NEW_GROUP_NAME);

        assertTrue(reservationList.doesReservationAlreadyExist(A_GROUP_NAME));
    }
}
