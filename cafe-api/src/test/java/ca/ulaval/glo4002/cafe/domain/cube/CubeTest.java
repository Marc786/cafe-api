package ca.ulaval.glo4002.cafe.domain.cube;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CubeTest {

    private static final CustomerId CUSTOMER_ID = new CustomerId("123");
    private static final CustomerId NEW_CUSTOMER_ID = new CustomerId("456");
    private static final CustomerId OTHER_CUSTOMER_ID = NEW_CUSTOMER_ID;
    private static final String CUBE_NAME = "polo";
    private static final SeatId SEAT_ID = new SeatId(99);
    private static final String A_GROUP_NAME = "mongroup";
    private final Seat seatMock1 = mock(Seat.class);
    private final Seat seatMock2 = mock(Seat.class);
    private Cube cube;

    @BeforeEach
    void createCube() {
        List<Seat> seats = createSeats();
        this.cube = new Cube(CUBE_NAME, seats);
    }

    @Test
    void onlyOneAvailableSeat_hasALoner_cubeHasLoner() {
        onlyFirstSeatAvailable();

        boolean hasALoner = cube.hasALoner();

        assertThat(hasALoner).isTrue();
    }

    @Test
    void moreThanOneAvailableSeat_hasALoner_cubeHasNoLoner() {
        allSeatAvailable();

        boolean hasALoner = cube.hasALoner();

        assertThat(hasALoner).isFalse();
    }

    @Test
    void numberOfSeatsExceedsByOneTheNumberOfAvailableSeats_willCreateALoner_createALoner() {
        int numberOfAvailableSeats = 2;
        allSeatAvailable();

        boolean createALoner = cube.willCreateALonerIfTryReserve(numberOfAvailableSeats + 1);

        assertThat(createALoner).isTrue();
    }

    @Test
    void numberOfSeatsExceedsByMoreThanOneTheNumberOfAvailableSeats_willCreateALoner_dontCreateALoner() {
        int numberOfAvailableSeats = 2;
        allSeatAvailable();

        boolean createALoner = cube.willCreateALonerIfTryReserve(numberOfAvailableSeats + 2);

        assertThat(createALoner).isFalse();
    }

    @Test
    void numberOfSeatsSmallerThanOneTheNumberOfAvailableSeats_willCreateALoner_dontCreateALoner() {
        int numberOfAvailableSeats = 2;
        allSeatAvailable();

        boolean createALoner = cube.willCreateALonerIfTryReserve(numberOfAvailableSeats - 1);

        assertThat(createALoner).isFalse();
    }

    @Test
    void customerWithSeat_getCustomerSeatNumber_returnsSeatNumber() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);
        when(seatMock1.getSeatId()).thenReturn(SEAT_ID);

        Optional<Seat> seatId = cube.findCustomerSeat(CUSTOMER_ID);

        assertThat(seatId.isPresent()).isTrue();
    }

    @Test
    void customerWithoutSeat_getCustomerSeatNumber_returnsNoSeatNumber() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);

        Optional<Seat> seatId = cube.findCustomerSeat(NEW_CUSTOMER_ID);

        assertThat(seatId.isPresent()).isFalse();
    }

    @Test
    void availableSeats_assignCustomer_customerIsAssigned() {
        when(seatMock1.assignCustomerIfPossible(CUSTOMER_ID)).thenReturn(true);

        boolean customerIsAssign = cube.assignCustomerIfPossible(CUSTOMER_ID);

        assertThat(customerIsAssign).isTrue();
    }

    @Test
    void noAvailableSeats_assignCustomer_customerIsNotAssigned() {
        when(seatMock1.assignCustomerIfPossible(CUSTOMER_ID)).thenReturn(false);

        boolean customerIsAssign = cube.assignCustomerIfPossible(CUSTOMER_ID);

        assertThat(customerIsAssign).isFalse();
    }

    @Test
    void availableSeatInGroup_assignCustomerToGroup_customerIsAssigned() {
        when(seatMock1.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME)).thenReturn(true);

        boolean customerIsAssign = cube.assignCustomerToGroupSeatIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(customerIsAssign).isTrue();
    }

    @Test
    void noAvailableSeatInGroup_assignCustomerToGroup_customerIsAssigned() {
        when(seatMock1.assignCustomerToGroupIfPossible(CUSTOMER_ID, A_GROUP_NAME)).thenReturn(false);

        boolean customerIsAssign = cube.assignCustomerToGroupSeatIfPossible(CUSTOMER_ID, A_GROUP_NAME);

        assertThat(customerIsAssign).isFalse();
    }

    @Test
    void setAllCubeSeatsAvailable_callsUnassignForAllCubeSeats() {
        cube.setAvailable();
        verify(seatMock1).unassignCustomer();
        verify(seatMock2).unassignCustomer();
    }

    @Test
    void moreAvailableSeatThanNumberToReserve_tryReserve_returnNumberOfReservedSeats() {
        int numberOfSeatsToReserve = 1;
        allSeatAvailable();

        int reservedSeats = cube.tryReserveSeats(numberOfSeatsToReserve, A_GROUP_NAME);

        assertThat(reservedSeats).isEqualTo(numberOfSeatsToReserve);
    }

    @Test
    void moreAvailableSeatThanNumberToReserve_tryReserve_firstSeatReserved() {
        int numberOfSeatsToReserve = 1;
        allSeatAvailable();

        cube.tryReserveSeats(numberOfSeatsToReserve, A_GROUP_NAME);

        verify(seatMock1).reserve(A_GROUP_NAME);
        verify(seatMock2, never()).reserve(A_GROUP_NAME);
    }

    @Test
    void lessAvailableSeatThanNumberToReserve_tryReserve_returnNumberOfReservedSeats() {
        int numberOfSeatsToReserve = 2;
        int expectedNumberOfReservedSeats = 1;
        onlySecondSeatAvailable();

        int reservedSeats = cube.tryReserveSeats(numberOfSeatsToReserve, A_GROUP_NAME);

        assertThat(reservedSeats).isEqualTo(expectedNumberOfReservedSeats);
    }

    @Test
    void lessAvailableSeatThanNumberToReserve_tryReserve_availableSeatsReserved() {
        int numberOfSeatsToReserve = 2;
        onlySecondSeatAvailable();

        cube.tryReserveSeats(numberOfSeatsToReserve, A_GROUP_NAME);

        verify(seatMock1, never()).reserve(A_GROUP_NAME);
        verify(seatMock2).reserve(A_GROUP_NAME);
    }

    @Test
    void allSeatsAvailable_tryReserveAllSeats_allSeatsAreReserved() {
        allSeatAvailable();

        cube.tryReserveAllSeats(A_GROUP_NAME);

        verify(seatMock1).reserve(A_GROUP_NAME);
        verify(seatMock2).reserve(A_GROUP_NAME);
    }

    @Test
    void allSeatsAvailable_tryReserveAllSeats_returnNumberOfReservedSeats() {
        int expectedNumberOfReservedSeats = 2;
        allSeatAvailable();

        int numberOfReservedSeats = cube.tryReserveAllSeats(A_GROUP_NAME);

        assertThat(numberOfReservedSeats).isEqualTo(expectedNumberOfReservedSeats);
    }

    @Test
    void atLeastOneSeatNotAvailable_tryReserveAllSeats_noSeatsAreReserved() {
        onlyFirstSeatAvailable();

        cube.tryReserveAllSeats(A_GROUP_NAME);

        verify(seatMock1, never()).reserve(A_GROUP_NAME);
        verify(seatMock2, never()).reserve(A_GROUP_NAME);
    }

    @Test
    void atLeastOneSeatNotAvailable_tryReserveAllSeats_returnAlwaysZero() {
        int expectedNumberOfReservedSeats = 0;
        onlyFirstSeatAvailable();

        int numberOfReservedSeats = cube.tryReserveAllSeats(A_GROUP_NAME);

        assertThat(numberOfReservedSeats).isEqualTo(expectedNumberOfReservedSeats);
    }

  /*  @Test
    void oneSeatInReservationGroup_isCustomerInAGroup_customerIsInAGroup() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);
        when(seatMock1.isInGroup()).thenReturn(true);

        boolean isCustomerInAGroup = cube.isCustomerInAGroup(CUSTOMER_ID);

        assertThat(isCustomerInAGroup).isTrue();
    }

    @Test
    void noSeatsInReservationGroup_isCustomerInAGroup_customerIsNotInAGroup() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);
        when(seatMock1.isInGroup()).thenReturn(false);

        boolean isCustomerInAGroup = cube.isCustomerInAGroup(CUSTOMER_ID);

        assertThat(isCustomerInAGroup).isFalse();
    }*/

    @Test
    void aSeatInAGroup_isGroupEmpty_groupIsNotEmpty() {
        when(seatMock1.isInGroupWithGroupName(any())).thenReturn(true);
        when(seatMock2.isInGroupWithGroupName(any())).thenReturn(false);

        boolean blah = cube.isGroupEmpty(A_GROUP_NAME);

        assertThat(blah).isFalse();
    }

    @Test
    void noSeatsInAGroup_isGroupEmpty_groupIsEmpty() {
        when(seatMock1.isInGroupWithGroupName(any())).thenReturn(false);
        when(seatMock2.isInGroupWithGroupName(any())).thenReturn(false);

        boolean groupIsEmpty = cube.isGroupEmpty(A_GROUP_NAME);

        assertThat(groupIsEmpty).isTrue();
    }

    @Test
    void customerWithoutGroup_unassignCustomerIfPossible_returnTrue() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);

        boolean customerIsUnassigned = cube.unassignCustomerIfPossible(CUSTOMER_ID);

        assertThat(customerIsUnassigned).isTrue();
    }

    @Test
    void customerWithoutGroup_unassignCustomerIfPossible_customerIsUnassigned() {
        when(seatMock1.getCustomerId()).thenReturn(CUSTOMER_ID);

        cube.unassignCustomerIfPossible(CUSTOMER_ID);

        verify(seatMock1).checkoutCustomer();
    }

    @Test
    void customerDoesNotExistInCube_unassignCustomerIfPossible_returnFalse() {
        when(seatMock1.getCustomerId()).thenReturn(OTHER_CUSTOMER_ID);

        boolean customerNotUnassigned = cube.unassignCustomerIfPossible(CUSTOMER_ID);

        assertThat(customerNotUnassigned).isFalse();
    }

    @Test
    void oneSeatWithGroup_unassignAllCustomersInGroup_onlyOneSeatToUnassign() {
        when(seatMock1.isInGroupWithGroupName(A_GROUP_NAME)).thenReturn(true);
        when(seatMock2.isInGroupWithGroupName(A_GROUP_NAME)).thenReturn(false);

        cube.changeAllReservationSeatsToAvailable(A_GROUP_NAME);

        verify(seatMock1).unassignCustomer();
        verify(seatMock2, never()).unassignCustomer();
    }

    @Test
    void noSeatsWithGroup_unassignAllCustomersInGroup_noSeatsToUnassign() {
        when(seatMock1.isInGroupWithGroupName(A_GROUP_NAME)).thenReturn(false);
        when(seatMock2.isInGroupWithGroupName(A_GROUP_NAME)).thenReturn(false);

        cube.changeAllReservationSeatsToAvailable(A_GROUP_NAME);

        verify(seatMock1, never()).unassignCustomer();
        verify(seatMock2, never()).unassignCustomer();
    }

    private List<Seat> createSeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(seatMock1);
        seats.add(seatMock2);

        return seats;
    }

    private void allSeatAvailable() {
        when(seatMock1.isAvailable()).thenReturn(true);
        when(seatMock2.isAvailable()).thenReturn(true);
    }

    private void onlyFirstSeatAvailable() {
        when(seatMock1.isAvailable()).thenReturn(true);
        when(seatMock2.isAvailable()).thenReturn(false);
    }

    private void onlySecondSeatAvailable() {
        when(seatMock1.isAvailable()).thenReturn(false);
        when(seatMock2.isAvailable()).thenReturn(true);
    }
}
