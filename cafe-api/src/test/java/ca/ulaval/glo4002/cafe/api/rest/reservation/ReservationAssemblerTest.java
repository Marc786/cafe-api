package ca.ulaval.glo4002.cafe.api.rest.reservation;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ReservationAssemblerTest {

    private final String GROUP_NAME = "name";
    private final int GROUP_SIZE = 2;

    @Test
    void reservation_convertToReservationResponses_returnExpectedReservationResponses() {
        ReservationAssembler reservationAssembler = new ReservationAssembler();
        List<ReservationDTO> reservations = createReservations();
        List<ReservationResponse> expectedReservationResponse = createExpectedReservationResponses();

        List<ReservationResponse> reservationResponses = reservationAssembler.toReservationResponses(reservations);

        assertThat(reservationResponses).isEqualTo(expectedReservationResponse);
    }

    private List<ReservationDTO> createReservations() {
        List<ReservationDTO> reservations = new ArrayList<>();
        reservations.add(new ReservationDTO(GROUP_NAME, GROUP_SIZE));

        return reservations;
    }

    private List<ReservationResponse> createExpectedReservationResponses() {
        List<ReservationResponse> expectedReservationResponse = new ArrayList<>();
        expectedReservationResponse.add(new ReservationResponse(GROUP_NAME, GROUP_SIZE));

        return expectedReservationResponse;
    }
}
