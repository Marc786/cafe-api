package ca.ulaval.glo4002.cafe.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.DuplicateGroupNameException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateReservationTest {

    private static final String GROUP_NAME = "ungroup";
    private static final int GROUP_SIZE = 3;

    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final ReservationFactory reservationFactoryMock = mock(ReservationFactory.class);
    private final Cafe cafeMock = mock(Cafe.class);

    private CreateReservation createReservation;

    @BeforeEach
    public void generateCreateReservation() {
        createReservation = new CreateReservation(cafeRepositoryMock, reservationFactoryMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void reservationAlreadyExists_createReservation_throwDuplicateGroupNameExceptionAndStopExecution() {
        when(cafeMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(true);

        assertThrows(DuplicateGroupNameException.class, () -> createReservation.create(GROUP_NAME, GROUP_SIZE));
    }

    @Test
    void validReservation_createReservation_reservationCreated() {
        when(cafeMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(false);

        createReservation.create(GROUP_NAME, GROUP_SIZE);

        verify(cafeMock).addReservation(any());
        verify(cafeRepositoryMock).save(cafeMock);
    }
}
