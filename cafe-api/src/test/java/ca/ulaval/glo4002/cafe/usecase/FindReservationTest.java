package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.usecase.assembler.ReservationDTOAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FindReservationTest {

    private static final String GROUP_NAME = "groupName";
    private static final int GROUP_SIZE = 4;
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final ReservationDTOAssembler reservationAssemblerMock = mock(ReservationDTOAssembler.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private FindReservation findReservation;

    @BeforeEach
    void setup() {
        findReservation = new FindReservation(cafeRepositoryMock, reservationAssemblerMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void findAllReservations_reservationToReservationDTOIsCalled() {
        List<Reservation> reservations = createListOfReservations();
        when(cafeMock.getReservations()).thenReturn(reservations);

        findReservation.findAllReservations();

        verify(reservationAssemblerMock).fromReservationsToReservationsDTO(reservations);
    }

    private List<Reservation> createListOfReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(GROUP_NAME, GROUP_SIZE));
        return reservations;
    }
}
