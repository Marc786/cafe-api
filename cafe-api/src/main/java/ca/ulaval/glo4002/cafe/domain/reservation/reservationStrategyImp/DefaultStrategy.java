package ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;

import java.util.Iterator;
import java.util.List;

public class DefaultStrategy implements ReservationStrategy {

    @Override
    public void reserve(List<Cube> cubes, Reservation reservation) {
        int remainingPlacesToSelect = reservation.getGroupSize();
        Iterator<Cube> cubesIterator = cubes.iterator();

        while (remainingPlacesToSelect > 0) {
            Cube currentCube = getNextCube(cubesIterator);
            remainingPlacesToSelect -= currentCube.tryReserveSeats(remainingPlacesToSelect, reservation.getGroupName());
        }
    }

    private Cube getNextCube(Iterator<Cube> cubes) {
        if (cubes.hasNext()) {
            return cubes.next();
        } else
            throw new InsufficientSeatsException();
    }
}
