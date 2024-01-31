package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfigFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.tax.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChangeReservationConfigTest {

    private static final String ORGANIZATION_NAME = "organization";
    private static final int CUBE_SIZE = 5;
    private static final ReservationType DEFAULT_RESERVATION_TYPE = ReservationType.DEFAULT;
    private static final String COUNTRY = "CA";
    private static final String PROVINCE = "QC";
    private static final String STATE = "";
    private static final double TIP = 0;
    private static final Location VALID_LOCATION = new Location(COUNTRY, PROVINCE, STATE);

    private final CafeConfig cafeConfigMock = mock(CafeConfig.class);
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final CafeConfigFactory cafeConfigFactoryMock = mock(CafeConfigFactory.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private final CafeFactory cafeFactoryMock = mock(CafeFactory.class);
    private ChangeReservationConfig changeReservationConfig;

    @BeforeEach
    void createChangeReservationConfig() {
        changeReservationConfig = new ChangeReservationConfig(cafeRepositoryMock, cafeConfigFactoryMock,
            cafeFactoryMock);
    }

    @Test
    void validReservationMethod_changeReservationMethod_changesConfigAndSetNewReservationMethod() {
        when(cafeConfigFactoryMock.create(DEFAULT_RESERVATION_TYPE, ORGANIZATION_NAME, CUBE_SIZE, VALID_LOCATION, TIP))
            .thenReturn(cafeConfigMock);
        when(cafeFactoryMock.create(cafeConfigMock)).thenReturn(cafeMock);

        changeReservationConfig.change(DEFAULT_RESERVATION_TYPE, ORGANIZATION_NAME, CUBE_SIZE, VALID_LOCATION, TIP);

        verify(cafeRepositoryMock).save(cafeMock);
    }
}
