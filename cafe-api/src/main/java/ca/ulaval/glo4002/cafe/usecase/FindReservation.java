package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.usecase.assembler.ReservationDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.ReservationDTO;

import java.util.List;

public class FindReservation {

    private final CafeRepository cafeRepository;
    private final ReservationDTOAssembler reservationAssembler;

    public FindReservation(CafeRepository cafeRepository, ReservationDTOAssembler reservationAssembler) {
        this.cafeRepository = cafeRepository;
        this.reservationAssembler = reservationAssembler;
    }

    public List<ReservationDTO> findAllReservations() {
        List<Reservation> reservations = cafeRepository.fetchCafe().getReservations();
        return reservationAssembler.fromReservationsToReservationsDTO(reservations);
    }
}
