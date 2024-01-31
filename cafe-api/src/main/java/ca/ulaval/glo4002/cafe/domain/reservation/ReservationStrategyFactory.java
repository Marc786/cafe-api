package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.FullCubeStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.NoLonersStrategy;

public class ReservationStrategyFactory {

    public ReservationStrategy create(ReservationType reservationType) {
        if (reservationType == ReservationType.FULL_CUBES) {
            return new FullCubeStrategy();
        } else if (reservationType == ReservationType.NO_LONERS) {
            return new NoLonersStrategy();
        } else
            return new DefaultStrategy();
    }
}
