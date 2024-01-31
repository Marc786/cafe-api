package ca.ulaval.glo4002.cafe.domain.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReservationList {

    private final List<Reservation> reservations;

    public ReservationList() {
        reservations = new ArrayList<>();
    }

    public ReservationList(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public void add(Reservation reservation) {
        reservations.add(reservation);
    }

    public void clear() {
        reservations.clear();
    }

    public boolean doesReservationAlreadyExist(String groupName) {
        return reservations.stream().anyMatch(reservation -> groupName.equals(reservation.getGroupName()));
    }

    public boolean isEmpty() {
        return reservations.isEmpty();
    }

    public void remove(String groupName) {
        reservations.removeIf(reservation -> reservation.isGroupNameEquals(groupName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationList that = (ReservationList) o;
        return Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservations);
    }
}
