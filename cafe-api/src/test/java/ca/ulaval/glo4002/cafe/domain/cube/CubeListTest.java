package ca.ulaval.glo4002.cafe.domain.cube;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class CubeListTest {

    private final static CustomerId A_CUSTOMER_ID = new CustomerId("customerid");
    private final static String A_GROUP_NAME = "groupname";
    private final Cube cubeMock = mock(Cube.class);
    private final Cube cubeMock2 = mock(Cube.class);
    private CubeList cubeList;

    @BeforeEach
    void createCubeList() {
        cubeList = new CubeList(List.of(cubeMock, cubeMock2));
    }

    @Test
    void customerInside_findCustomerSeat_returnCustomerSeat() {
        Seat expectedSeat = new Seat(new SeatId(1), Status.OCCUPIED);
        when(cubeMock.findCustomerSeat(A_CUSTOMER_ID)).thenReturn(Optional.of(expectedSeat));

        Seat seat = cubeList.findCustomerSeat(A_CUSTOMER_ID);

        assertThat(seat).isEqualTo(expectedSeat);
    }

    @Test
    void customerNotInside_findCustomerSeat_shouldThrowsCustomerNotFoundException() {
        when(cubeMock.findCustomerSeat(A_CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> cubeList.findCustomerSeat(A_CUSTOMER_ID));
    }

    @Test
    void enoughSeat_assignCustomerToFirstFreeSeat_customerIsAssigned() {
        when(cubeMock.assignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(true);

        cubeList.assignCustomerToFirstFreeSeat(A_CUSTOMER_ID);

        verify(cubeMock).assignCustomerIfPossible(A_CUSTOMER_ID);
    }

    @Test
    void notEnoughSeat_assignCustomerToFirstFreeSeat_shouldThrowsInsufficientSeatsException() {
        when(cubeMock.assignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(false);

        assertThrows(InsufficientSeatsException.class, () -> cubeList.assignCustomerToFirstFreeSeat(A_CUSTOMER_ID));
    }

    @Test
    void enoughSeatInReservation_assignCustomerToReservationSeat_customerIsAssignedToReservation() {
        when(cubeMock.assignCustomerToGroupSeatIfPossible(A_CUSTOMER_ID, A_GROUP_NAME)).thenReturn(true);

        cubeList.assignCustomerToReservationFirstFreeSeat(A_CUSTOMER_ID, A_GROUP_NAME);

        verify(cubeMock).assignCustomerToGroupSeatIfPossible(A_CUSTOMER_ID, A_GROUP_NAME);
    }

    @Test
    void notEnoughSeatInReservation_assignCustomerToReservationSeat_shouldThrowsNoGroupSeatsException() {
        when(cubeMock.assignCustomerToGroupSeatIfPossible(A_CUSTOMER_ID, A_GROUP_NAME)).thenReturn(false);

        assertThrows(NoGroupSeatsException.class, () ->
            cubeList.assignCustomerToReservationFirstFreeSeat(A_CUSTOMER_ID, A_GROUP_NAME));
    }

    @Test
    void setAllSeatAvailable_shouldSetAvailable() {
        cubeList.setAllSeatsAvailable();

        verify(cubeMock).setAvailable();
    }

    @Test
    void noCustomersInGroup_unassignCustomer_unassignsCustomerIfPossible() {
        when(cubeMock.unassignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(true);
        when(cubeMock.isGroupEmpty(A_GROUP_NAME)).thenReturn(false);

        cubeList.unassignCustomerIsLastInGroup(A_CUSTOMER_ID, A_GROUP_NAME);

        verify(cubeMock).unassignCustomerIfPossible(A_CUSTOMER_ID);
        verify(cubeMock).isGroupEmpty(A_GROUP_NAME);
        verify(cubeMock, never()).changeAllReservationSeatsToAvailable(A_GROUP_NAME);
    }

    @Test
    void noCustomers_unassignCustomer_throwsCustomerNotFoundException() {
        when(cubeMock.unassignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(false);

        assertThrows(CustomerNotFoundException.class, () ->
            cubeList.unassignCustomerIsLastInGroup(A_CUSTOMER_ID, A_GROUP_NAME));
    }

    @Test
    void customerInAFullGroup_unassignCustomerIsLastInGroup_unassignsCustomer() {
        when(cubeMock.unassignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(true);
        when(cubeMock.isGroupEmpty(A_GROUP_NAME)).thenReturn(false);
        when(cubeMock2.isGroupEmpty(A_GROUP_NAME)).thenReturn(true);

        cubeList.unassignCustomerIsLastInGroup(A_CUSTOMER_ID, A_GROUP_NAME);

        verify(cubeMock).unassignCustomerIfPossible(A_CUSTOMER_ID);
        verify(cubeMock, never()).changeAllReservationSeatsToAvailable(A_GROUP_NAME);
    }

    @Test
    void lastCustomerInAGroup_unassignCustomerIsLastInGroup_unassignsAllCustomersInGroup2() {
        when(cubeMock2.unassignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(true);
        when(cubeMock.isGroupEmpty(A_GROUP_NAME)).thenReturn(true);
        when(cubeMock2.isGroupEmpty(A_GROUP_NAME)).thenReturn(true);

        boolean lastCustomerInGroup = cubeList.unassignCustomerIsLastInGroup(A_CUSTOMER_ID, A_GROUP_NAME);

        assertThat(lastCustomerInGroup).isTrue();
    }

    @Test
    void lastCustomerInAGroup_unassignCustomerIsLastInGroup_unassignsAllCustomersInGroup() {
        when(cubeMock2.unassignCustomerIfPossible(A_CUSTOMER_ID)).thenReturn(true);
        when(cubeMock.isGroupEmpty(A_GROUP_NAME)).thenReturn(true);
        when(cubeMock2.isGroupEmpty(A_GROUP_NAME)).thenReturn(true);

        cubeList.unassignCustomerIsLastInGroup(A_CUSTOMER_ID, A_GROUP_NAME);

        verify(cubeMock).unassignCustomerIfPossible(A_CUSTOMER_ID);
        verify(cubeMock).changeAllReservationSeatsToAvailable(A_GROUP_NAME);
        verify(cubeMock2).unassignCustomerIfPossible(A_CUSTOMER_ID);
        verify(cubeMock2).changeAllReservationSeatsToAvailable(A_GROUP_NAME);
    }
}
