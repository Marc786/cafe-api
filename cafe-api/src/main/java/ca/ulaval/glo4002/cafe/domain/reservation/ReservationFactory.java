package ca.ulaval.glo4002.cafe.domain.reservation;


public class ReservationFactory {

    public Reservation create(String groupName, int groupSize) {
        return new Reservation(groupName, groupSize);
    }
}
