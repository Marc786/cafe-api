package ca.ulaval.glo4002.cafe.domain.seat;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import org.junit.jupiter.api.Test;

class SeatTest {

    private static final SeatId SEAT_ID_1 = new SeatId(1);
    private static final SeatId SEAT_ID_2 = new SeatId(99);
    private static final CustomerId CUSTOMER_ID = new CustomerId("123");
    private static final String A_GROUP_NAME = "mongroup";
    private static final String GROUP_NAME_2 = "group2";
    private static final String WRONG_GROUP_NAME = GROUP_NAME_2;

    @Test
    void availableSeat_isAvailable_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        assertThat(seat.isAvailable()).isTrue();
    }

    @Test
    void occupiedSeat_isAvailable_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.OCCUPIED);

        assertThat(seat.isAvailable()).isFalse();
    }

    @Test
    void reservedSeat_isReserved_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED);

        assertThat(seat.isReserved()).isTrue();
    }

    @Test
    void availableSeat_isReserved_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        assertThat(seat.isReserved()).isFalse();
    }

    @Test
    void occupiedSeat_isReserved_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.OCCUPIED);

        assertThat(seat.isReserved()).isFalse();
    }

    @Test
    void seatHasAGroup_isInGroupWithGoodGroupName_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED, CUSTOMER_ID, A_GROUP_NAME);

        assertThat(seat.isInGroupWithGroupName(A_GROUP_NAME)).isTrue();
    }

    @Test
    void seatHasNoGroup_isInGroupWithGoodGroupName_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        assertThat(seat.isInGroupWithGroupName(A_GROUP_NAME)).isFalse();
    }

    @Test
    void seatHasAGroup_isInGroupWithWrongGroupName_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED, CUSTOMER_ID, A_GROUP_NAME);

        assertThat(seat.isInGroupWithGroupName(WRONG_GROUP_NAME)).isFalse();
    }

    @Test
    void availableSeat_assignCustomer_successfullyAssignedCustomer() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        seat.assignCustomer(CUSTOMER_ID);

        assertThat(seat.getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(seat.getStatus()).isEqualTo(Status.OCCUPIED);
    }

    @Test
    void twoSameSeats_areEquals_returnTrue() {
        Seat seat1 = new Seat(SEAT_ID_1, Status.AVAILABLE);
        Seat seat2 = new Seat(SEAT_ID_1, Status.AVAILABLE);

        boolean areEquals = seat1.equals(seat2);

        assertThat(areEquals).isTrue();
    }

    @Test
    void twoDifferentSeats_areEquals_returnFalse() {
        Seat seat1 = new Seat(SEAT_ID_1, Status.AVAILABLE);
        Seat seat2 = new Seat(SEAT_ID_2, Status.OCCUPIED);

        boolean areEquals = seat1.equals(seat2);

        assertThat(areEquals).isFalse();
    }

    @Test
    void availableSeat_assignCustomer_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        boolean isAssigned = seat.assignCustomerIfPossible(CUSTOMER_ID);

        assertThat(isAssigned).isTrue();
    }

    @Test
    void occupiedSeat_assignCustomer_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.OCCUPIED);

        boolean isAssigned = seat.assignCustomerIfPossible(CUSTOMER_ID);

        assertThat(isAssigned).isFalse();
    }

    @Test
    void reservedSeat_assignCustomerToGroup_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED, null, A_GROUP_NAME);

        boolean isAssigned = seat.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(isAssigned).isTrue();
    }

    @Test
    void occupiedSeat_assignCustomerToGroup_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.OCCUPIED);

        boolean isAssigned = seat.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(isAssigned).isFalse();
    }

    @Test
    void availableSeat_assignCustomerToGroup_returnFalse() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        boolean isAssigned = seat.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(isAssigned).isFalse();
    }

    @Test
    void reservedSeatNotSameGroup_assignCustomerToGroup_returnTrue() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED, null, GROUP_NAME_2);

        boolean isAssigned = seat.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(isAssigned).isFalse();
    }

    @Test
    void occupiedSeat_unassignCustomer_seatIsAvailable() {
        Seat seat = new Seat(SEAT_ID_1, Status.RESERVED);

        seat.unassignCustomer();

        assertThat(seat.getStatus()).isEqualTo(Status.AVAILABLE);
        assertThat(seat.getCustomerId()).isEqualTo(null);
        assertThat(seat.getGroupName()).isEqualTo(null);
    }

    @Test
    void reserveSeat_seatIsReserved() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE);

        seat.reserve(A_GROUP_NAME);

        assertThat(seat.getStatus()).isEqualTo(Status.RESERVED);
    }

    @Test
    void seatNotInAGroup_checkoutCustomer_unassignsCustomer() {
        Seat seat = new Seat(SEAT_ID_1, Status.AVAILABLE, CUSTOMER_ID, null);

        seat.checkoutCustomer();
        assertThat(seat.getCustomerId()).isEqualTo(null);
        assertThat(seat.getGroupName()).isEqualTo(null);
        assertThat(seat.getStatus()).isEqualTo(Status.AVAILABLE);
    }
}
