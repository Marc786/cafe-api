package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.DuplicateGroupNameException;

public class CreateReservation {

    private final CafeRepository cafeRepository;
    private final ReservationFactory reservationFactory;

    public CreateReservation(CafeRepository cafeRepository, ReservationFactory reservationFactory) {
        this.cafeRepository = cafeRepository;
        this.reservationFactory = reservationFactory;
    }

    public void create(String groupName, int groupSize) {
        Cafe cafe = cafeRepository.fetchCafe();

        if (cafe.doesReservationAlreadyExist(groupName)) {
            throw new DuplicateGroupNameException();
        }

        Reservation reservation = reservationFactory.create(groupName, groupSize);
        cafe.addReservation(reservation);

        cafeRepository.save(cafe);
    }
}
