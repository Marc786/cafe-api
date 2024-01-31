package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.anyInt;
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

class DefaultStrategyTest {

    private static final String GROUP_NAME = "Les fées ont soif";

    private final Cube cube1 = mock(Cube.class);
    private final Cube cube2 = mock(Cube.class);

    private List<Cube> cubes;
    private DefaultStrategy defaultStrategy;

    @BeforeEach
    void createCubes() {
        defaultStrategy = new DefaultStrategy();
        cubes = List.of(cube1, cube2);
        twoAvailableSeatsPerCube();
    }

    @Test
    void notEnoughFreeSeats_tryReserveSeats_ThrowsInsufficientSeatsException() {
        int invalidReservationSize = 5;
        Reservation reservation = new Reservation(GROUP_NAME, invalidReservationSize);

        assertThrows(InsufficientSeatsException.class, () -> defaultStrategy.reserve(cubes, reservation));
    }

    @Test
    void enoughFreeSeatsInFirstCube_tryReserveSeats_reserveSeatsInFirstCube() {
        int reservationSize = 2;
        Reservation reservation = new Reservation(GROUP_NAME, reservationSize);

        defaultStrategy.reserve(cubes, reservation);

        verify(cube1).tryReserveSeats(reservationSize, reservation.getGroupName());
    }

    @Test
    void enoughFreeSeatsInFirstCube_tryReserveSeats_dontTryReserveInOtherCubeAfterSeatsReserved() {
        int reservationSize = 2;
        Reservation reservation = new Reservation(GROUP_NAME, reservationSize);

        defaultStrategy.reserve(cubes, reservation);

        verify(cube2, never()).tryReserveSeats(reservationSize, reservation.getGroupName());
    }

    private void twoAvailableSeatsPerCube() {
        when(cube1.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);
        when(cube2.tryReserveSeats(anyInt(), eq(GROUP_NAME))).thenReturn(2);
    }
}
