package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import java.util.List;

public class ReservationDTOAssembler {

    public List<ReservationDTO> fromReservationsToReservationsDTO(List<Reservation> reservations) {
        return reservations.stream()
            .map(reservation -> new ReservationDTO(reservation.getGroupName(), reservation.getGroupSize()))
            .toList();
    }
}
