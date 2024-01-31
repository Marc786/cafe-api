package ca.ulaval.glo4002.cafe.api.rest.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.usecase.CreateReservation;
import ca.ulaval.glo4002.cafe.usecase.FindReservation;
import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ReservationResourceTest {

    private static final String GROUP_NAME_1 = "les poils";
    private static final int GROUP_SIZE_1 = 5;
    private static final String GROUP_NAME_2 = "les yos";
    private static final int GROUP_SIZE_2 = 6;
    private final CreateReservation createReservation = mock(CreateReservation.class);
    private final FindReservation findReservation = mock(FindReservation.class);
    private final ReservationAssembler reservationAssembler = new ReservationAssembler();

    private ReservationResource reservationResource;

    @BeforeEach
    void createReservationResource() {
        reservationResource = new ReservationResource(createReservation, findReservation, reservationAssembler);
    }

    @Test
    void reservationRequest_addReservation_createReservationIsCalled() {
        ReservationRequest reservationRequest = new ReservationRequest(GROUP_NAME_1, GROUP_SIZE_1);
        ReservationParams reservationParams = createReservationParams(reservationRequest);

        reservationResource.addReservation(reservationRequest);

        verify(createReservation).create(reservationParams.getGroupName(), reservationParams.getGroupSize());
    }

    @Test
    void reservationRequest_addReservation_returnsExpectedResponse() {
        ReservationRequest reservationRequest = new ReservationRequest(GROUP_NAME_1, GROUP_SIZE_1);

        Response response = reservationResource.addReservation(reservationRequest);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void getReservations_returnsExpectedResponse() {
        when(findReservation.findAllReservations()).thenReturn(createReservationsDTO());
        List<ReservationResponse> expectedReservationResponses = createReservationResponses();

        Response response = reservationResource.getReservations();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(expectedReservationResponses);
    }

    private List<ReservationResponse> createReservationResponses() {
        return List.of(new ReservationResponse(GROUP_NAME_1, GROUP_SIZE_1),
            new ReservationResponse(GROUP_NAME_2, GROUP_SIZE_2));
    }

    private List<ReservationDTO> createReservationsDTO() {
        return List.of(new ReservationDTO(GROUP_NAME_1, GROUP_SIZE_1), new ReservationDTO(GROUP_NAME_2, GROUP_SIZE_2));
    }

    private ReservationParams createReservationParams(ReservationRequest reservationRequest) {
        return new ReservationParams(reservationRequest.groupName(), reservationRequest.groupSize());
    }
}
