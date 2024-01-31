package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.FullCubeStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryStrategy;

public class InMemoryStrategyAssembler {

    ReservationStrategyFactory reservationStrategyFactory;

    public InMemoryStrategyAssembler(ReservationStrategyFactory reservationStrategyFactory) {
        this.reservationStrategyFactory = reservationStrategyFactory;
    }

    public InMemoryStrategy fromReservationStrategyToInMemoryStrategy(ReservationStrategy reservationStrategy) {
        return new InMemoryStrategy(convertReservationStrategyToReservationType(reservationStrategy));
    }

    public ReservationStrategy fromInMemoryStrategyToReservationStrategy(InMemoryStrategy inMemoryStrategy) {
        return convertReservationTypeToReservationStrategy(inMemoryStrategy);
    }

    private ReservationType convertReservationStrategyToReservationType(ReservationStrategy reservationStrategy) {
        if (reservationStrategy instanceof NoLonersStrategy)
            return ReservationType.NO_LONERS;
        else if (reservationStrategy instanceof FullCubeStrategy)
            return ReservationType.FULL_CUBES;
        else
            return ReservationType.DEFAULT;
    }

    private ReservationStrategy convertReservationTypeToReservationStrategy(InMemoryStrategy inMemoryStrategy) {
        return reservationStrategyFactory.create(inMemoryStrategy.getReservationType());
    }
}
