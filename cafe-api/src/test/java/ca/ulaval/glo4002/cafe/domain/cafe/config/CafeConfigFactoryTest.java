package ca.ulaval.glo4002.cafe.domain.cafe.config;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CafeConfigFactoryTest {

    private static final ReservationType NO_LONERS_RESERVATION_TYPE = ReservationType.NO_LONERS;
    private static final String ORGANIZATION_NAME = "organization";
    private static final int CUBE_SIZE = 5;
    private static final String COUNTRY = "CA";
    private static final String PROVINCE = "QC";
    private static final String STATE = "";
    private static final double TIP = 0;
    private static final Location VALID_LOCATION = new Location(COUNTRY, PROVINCE, STATE);

    private final ReservationStrategyFactory reservationStrategyFactory = mock(ReservationStrategyFactory.class);
    private CafeConfigFactory cafeConfigFactory;

    @BeforeEach
    void setup() {
        cafeConfigFactory = new CafeConfigFactory(reservationStrategyFactory);
    }

    @Test
    void noLonersStrategy_createCafeConfig_returnExpectedConfig() {
        ReservationStrategy reservationStrategy = new NoLonersStrategy();
        when(reservationStrategyFactory.create(ReservationType.NO_LONERS)).thenReturn(reservationStrategy);

        CafeConfig cafeConfig = cafeConfigFactory.create(NO_LONERS_RESERVATION_TYPE, ORGANIZATION_NAME, CUBE_SIZE,
            VALID_LOCATION, TIP);

        assertThat(cafeConfig.getReservationStrategy()).isEqualTo(reservationStrategy);
    }
}
