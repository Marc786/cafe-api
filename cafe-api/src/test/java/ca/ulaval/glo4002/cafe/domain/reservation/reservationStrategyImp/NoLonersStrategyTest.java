package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class NoLonersStrategyTest {


    private static final String GROUP_NAME = "Les fées ont soif";

    private final Cube cube1Mock = mock(Cube.class);
    private final Cube cube2Mock = mock(Cube.class);

    private List<Cube> cubes;
    private NoLonersStrategy noLonersStrategy;

    @BeforeEach
    void createCubes() {
        noLonersStrategy = new NoLonersStrategy();
        cubes = List.of(cube1Mock, cube2Mock);
    }

    @Test
    void notEnoughFreeSeats_reserveSeats_ThrowsInsufficientSeatsException() {
        int notEnoughSeat = 7;
        Reservation reservation = new Reservation(GROUP_NAME, notEnoughSeat);
        twoCubesWith3AvailableSeats();

        assertThrows(InsufficientSeatsException.class, () -> noLonersStrategy.reserve(cubes, reservation));
    }

    @Test
    void enoughFreeSeatsInFirstCube_reserveSeats_tryReserveInFirstCubeOnly() {
        int groupSizeSameAsFirstCubeSize = 3;
        Reservation reservation = new Reservation(GROUP_NAME, groupSizeSameAsFirstCubeSize);
        twoCubesWith3AvailableSeats();

        noLonersStrategy.reserve(cubes, reservation);

        verify(cube1Mock).tryReserveSeats(3, GROUP_NAME);
        verify(cube2Mock, never()).tryReserveSeats(anyInt(), eq(GROUP_NAME));
    }

    @Test
    void lonerInFirstCube_reserveSeats_tryReserveInSecondCube() {
        int groupSize = 3;
        Reservation reservation = new Reservation(GROUP_NAME, groupSize);
        lonerInFirstCubeAndSecondCubeWith3Seats();

        noLonersStrategy.reserve(cubes, reservation);

        verify(cube1Mock, never()).tryReserveSeats(anyInt(), eq(GROUP_NAME));
        verify(cube2Mock).tryReserveSeats(3, GROUP_NAME);
    }

    @Test
    void twoFreeSeatsInFirstCube_reserveSeats_tryReserveInSecondCube() {
        int validGroupSize = 3;
        Reservation reservation = new Reservation(GROUP_NAME, validGroupSize);
        twoSeatsInFirstCubeAndSecondCubeWith3Seats();

        noLonersStrategy.reserve(cubes, reservation);

        verify(cube1Mock, never()).tryReserveSeats(anyInt(), eq(GROUP_NAME));
        verify(cube2Mock).tryReserveSeats(3, GROUP_NAME);
    }

    @Test
    void threeSeatsPerCubeWithReservationOfFour_reserveSeats_tryReserveInEachCube() {
        int validGroupSize = 4;
        Reservation reservation = new Reservation(GROUP_NAME, validGroupSize);
        twoCubesWith3AvailableSeatsReserveTwoInEach();

        noLonersStrategy.reserve(cubes, reservation);

        verify(cube1Mock).tryReserveSeats(2, GROUP_NAME);
        verify(cube2Mock).tryReserveSeats(2, GROUP_NAME);
    }

    @Test
    void threeSeatsPerCubeWithReservationOfFive_reserveSeats_fillFirstCubeAndSelectTwoInSecondCube() {
        int validGroupSize = 5;
        Reservation reservation = new Reservation(GROUP_NAME, validGroupSize);
        twoCubesWith3AvailableSeatsFillFirstAndTwoInSecond();

        noLonersStrategy.reserve(cubes, reservation);

        verify(cube1Mock).tryReserveSeats(5, GROUP_NAME);
        verify(cube2Mock).tryReserveSeats(2, GROUP_NAME);
    }


    private void twoCubesWith3AvailableSeats() {
        when(cube1Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube1Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(3);

        when(cube2Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube2Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(3);
    }

    private void lonerInFirstCubeAndSecondCubeWith3Seats() {
        when(cube1Mock.hasALoner()).thenReturn(true);

        when(cube2Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube2Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(3);
    }

    private void twoSeatsInFirstCubeAndSecondCubeWith3Seats() {
        when(cube1Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(true);
        when(cube1Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);

        when(cube2Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube2Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(3);
    }

    private void twoCubesWith3AvailableSeatsReserveTwoInEach() {
        when(cube1Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(true);
        when(cube1Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);

        when(cube2Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube2Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);
    }

    private void twoCubesWith3AvailableSeatsFillFirstAndTwoInSecond() {
        when(cube1Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube1Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(3);

        when(cube2Mock.willCreateALonerIfTryReserve(anyInt())).thenReturn(false);
        when(cube2Mock.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);
    }
}
