package ca.ulaval.glo4002.cafe.api.configuration.resource;

import ca.ulaval.glo4002.cafe.api.rest.reservation.ReservationAssembler;
import ca.ulaval.glo4002.cafe.api.rest.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.usecase.CreateReservation;
import ca.ulaval.glo4002.cafe.usecase.FindReservation;
import ca.ulaval.glo4002.cafe.usecase.assembler.ReservationDTOAssembler;

public class ReservationResourceFactory {

    private final CreateReservation createReservation;
    private final FindReservation findReservation;

    public ReservationResourceFactory(CafeRepository cafeRepository) {
        ReservationFactory reservationFactory = new ReservationFactory();
        ReservationDTOAssembler reservationDTOAssembler = new ReservationDTOAssembler();
        this.createReservation = new CreateReservation(cafeRepository, reservationFactory);
        this.findReservation = new FindReservation(cafeRepository, reservationDTOAssembler);
    }

    public ReservationResource create() {
        ReservationAssembler reservationAssembler = new ReservationAssembler();
        return new ReservationResource(createReservation, findReservation, reservationAssembler);
    }
}
