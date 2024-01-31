package ca.ulaval.glo4002.cafe.api.rest.reservation;

import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import java.util.List;

public class ReservationAssembler {

    public List<ReservationResponse> toReservationResponses(List<ReservationDTO> reservations) {
        return reservations.stream().map(this::createReservationResponse).toList();
    }

    private ReservationResponse createReservationResponse(ReservationDTO reservation) {
        return new ReservationResponse(reservation.groupName(), reservation.size());
    }
}
