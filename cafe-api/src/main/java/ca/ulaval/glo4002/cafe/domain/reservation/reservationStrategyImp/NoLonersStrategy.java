package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;

import java.util.Iterator;
import java.util.List;

public class NoLonersStrategy implements ReservationStrategy {

    @Override
    public void reserve(List<Cube> cubes, Reservation reservation) {
        int remainingPlacesToSelect = reservation.getGroupSize();
        String groupName = reservation.getGroupName();
        Iterator<Cube> cubesIterator = cubes.iterator();

        while (remainingPlacesToSelect > 0) {
            Cube currentCube = getNextCubeWithoutLoner(cubesIterator);

            if (!currentCube.willCreateALonerIfTryReserve(remainingPlacesToSelect))
                remainingPlacesToSelect -= tryReserveRemainingSeats(currentCube, remainingPlacesToSelect, groupName);

            else if (wontCreateLonerInCurrentOrNextCube(currentCube, remainingPlacesToSelect))
                remainingPlacesToSelect -= tryReserveRemainingSeatsWithoutMakingLoner(currentCube,
                    remainingPlacesToSelect, groupName);
        }
    }

    private Cube getNextCubeWithoutLoner(Iterator<Cube> cubes) {
        while (cubes.hasNext()) {
            Cube currentCube = cubes.next();
            if (!currentCube.hasALoner())
                return currentCube;
        }
        throw new InsufficientSeatsException();
    }

    private boolean wontCreateLonerInCurrentOrNextCube(Cube cube, int remainingPlacesToSelect) {
        return cube.willCreateALonerIfTryReserve(remainingPlacesToSelect) && dontMakeLonerInCurrentCube(
            remainingPlacesToSelect);
    }

    private boolean dontMakeLonerInCurrentCube(int remainingPlacesToSelect) {
        return remainingPlacesToSelect - 2 != 1;
    }

    private int tryReserveRemainingSeats(Cube currentCube, int remainingPlacesToSelect, String groupName) {
        return currentCube.tryReserveSeats(remainingPlacesToSelect, groupName);
    }

    private int tryReserveRemainingSeatsWithoutMakingLoner(Cube currentCube, int remainingPlacesToSelect,
        String groupName) {
        return currentCube.tryReserveSeats(remainingPlacesToSelect - 2, groupName);
    }
}
