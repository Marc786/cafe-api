package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryReservation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryReservationAssembler {

    public List<InMemoryReservation> fromReservationsToInMemoryReservations(List<Reservation> reservations) {
        return reservations.stream().map(this::createInMemoryReservation)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Reservation> fromInMemoryReservationsToReservations(
        List<InMemoryReservation> inMemoryReservations) {
        return inMemoryReservations.stream().map(this::createReservation)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private InMemoryReservation createInMemoryReservation(Reservation reservation) {
        return new InMemoryReservation(reservation.getGroupName(), reservation.getGroupSize());
    }

    private Reservation createReservation(InMemoryReservation inMemoryReservation) {
        return new Reservation(inMemoryReservation.getGroupName(), inMemoryReservation.getGroupSize());
    }
}
