package ca.ulaval.glo4002.cafe.usecase.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ReservationDTOAssemblerTest {

    private static final String GROUP_NAME = "les yo";
    private static final int GROUP_SIZE = 1;

    @Test
    void convertReservations_returnExpectedReservation() {
        ReservationDTOAssembler reservationAssembler = new ReservationDTOAssembler();
        List<Reservation> reservations = createReservations();
        ReservationDTO expectedReservation = new ReservationDTO(GROUP_NAME, GROUP_SIZE);

        List<ReservationDTO> reservationsDTO = reservationAssembler.fromReservationsToReservationsDTO(reservations);
        ReservationDTO reservation = reservationsDTO.get(0);

        assertThat(reservation).isEqualTo(expectedReservation);
    }

    private List<Reservation> createReservations() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(GROUP_NAME, GROUP_SIZE));
        return reservations;
    }
}
