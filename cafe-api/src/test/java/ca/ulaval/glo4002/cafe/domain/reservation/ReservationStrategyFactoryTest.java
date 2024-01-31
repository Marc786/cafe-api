package ca.ulaval.glo4002.cafe.domain.reservation;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.FullCubeStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.NoLonersStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservationStrategyFactoryTest {

    private final ReservationType defaultReservation = ReservationType.DEFAULT;
    private final ReservationType fullCubesReservation = ReservationType.FULL_CUBES;
    private final ReservationType noLonersReservation = ReservationType.NO_LONERS;
    private ReservationStrategyFactory reservationStrategyFactory;

    @BeforeEach
    void createReservationStrategyFactory() {
        reservationStrategyFactory = new ReservationStrategyFactory();
    }

    @Test
    void defaultReservationType_createReservationStrategy_createsDefaultStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.create(defaultReservation);

        assertThat(reservationStrategy).isInstanceOf(DefaultStrategy.class);
    }

    @Test
    void fullCubesReservationType_createReservationStrategy_createsFullCubesStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.create(fullCubesReservation);

        assertThat(reservationStrategy).isInstanceOf(FullCubeStrategy.class);
    }

    @Test
    void noLonersReservationType_createReservationStrategy_createsNoLonersStrategy() {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.create(noLonersReservation);

        assertThat(reservationStrategy).isInstanceOf(NoLonersStrategy.class);
    }
}
