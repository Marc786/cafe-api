package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp;

import static org.junit.Assert.assertThrows;
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

class FullCubeStrategyTest {

    private static final String GROUP_NAME = "Les fées ont soif";

    private final Cube cube1Mock = mock(Cube.class);
    private final Cube cube2Mock = mock(Cube.class);

    private List<Cube> cubes;
    private FullCubeStrategy fullCubeStrategy;

    @BeforeEach
    void createCubesAndSeats() {
        fullCubeStrategy = new FullCubeStrategy();
        cubes = List.of(cube1Mock, cube2Mock);
    }

    @Test
    void noAvailableCube_tryReserve_ThrowsInsufficientSeatsException() {
        int aReservationSize = 3;
        Reservation reservation = new Reservation(GROUP_NAME, aReservationSize);
        noAvailableCube();

        assertThrows(InsufficientSeatsException.class, () -> fullCubeStrategy.reserve(cubes, reservation));
    }

    @Test
    void availableCubesAndReservationSmallerThanCubeSize_tryReserve_reserveFirstAvailableCube() {
        int reservationSizeSmallerThanCubeSize = 3;
        Reservation reservation = new Reservation(GROUP_NAME, reservationSizeSmallerThanCubeSize);
        availableCubesOfFourSeats();

        fullCubeStrategy.reserve(cubes, reservation);

        verify(cube1Mock).tryReserveAllSeats(reservation.getGroupName());
    }

    @Test
    void availableCubesAndReservationSmallerThanCubeSize_tryReserve_dontTryReserveOtherCubeAfterSeatsReserved() {
        int reservationSizeSmallerThanCubeSize = 4;
        Reservation reservation = new Reservation(GROUP_NAME, reservationSizeSmallerThanCubeSize);
        availableCubesOfFourSeats();

        fullCubeStrategy.reserve(cubes, reservation);

        verify(cube2Mock, never()).tryReserveAllSeats(reservation.getGroupName());
    }

    @Test
    void availableCubesAndReservationBiggerThanOneCubeSize_tryReserve_reserveMultipleCubes() {
        int reservationSizeBiggerThanOneCubeSize = 5;
        Reservation reservation = new Reservation(GROUP_NAME, reservationSizeBiggerThanOneCubeSize);
        availableCubesOfFourSeats();

        fullCubeStrategy.reserve(cubes, reservation);

        verify(cube1Mock).tryReserveAllSeats(reservation.getGroupName());
        verify(cube2Mock).tryReserveAllSeats(reservation.getGroupName());
    }

    private void noAvailableCube() {
        when(cube1Mock.tryReserveAllSeats(GROUP_NAME)).thenReturn(0);
        when(cube2Mock.tryReserveAllSeats(GROUP_NAME)).thenReturn(0);
    }

    private void availableCubesOfFourSeats() {
        when(cube1Mock.tryReserveAllSeats(GROUP_NAME)).thenReturn(4);
        when(cube2Mock.tryReserveAllSeats(GROUP_NAME)).thenReturn(4);
    }
}
