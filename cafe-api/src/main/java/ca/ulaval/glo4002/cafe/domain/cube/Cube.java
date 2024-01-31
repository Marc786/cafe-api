package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Cube {

    private final String name;
    private final List<Seat> seats;

    public Cube(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Optional<Seat> findCustomerSeat(CustomerId customerId) {
        return seats.stream().filter(seat -> customerId.equals(seat.getCustomerId())).findFirst();
    }

    public boolean assignCustomerIfPossible(CustomerId customerId) {
        return seats.stream().anyMatch(seat -> seat.assignCustomerIfPossible(customerId));
    }

    public boolean unassignCustomerIfPossible(CustomerId customerId) {
        Optional<Seat> seat = findCustomerSeat(customerId);
        if (seat.isPresent()) {
            seat.get().checkoutCustomer();
            return true;
        }
        return false;
    }

    public boolean assignCustomerToGroupSeatIfPossible(CustomerId customerId, String groupName) {
        return seats.stream().anyMatch(seat -> seat.assignCustomerToGroupIfPossible(customerId, groupName));
    }

    public void setAvailable() {
        seats.forEach(Seat::unassignCustomer);
    }

    public int tryReserveSeats(int numberOfSeatsToReserve, String groupName) {
        AtomicInteger reservedSeatCount = new AtomicInteger();
        seats.stream().filter(Seat::isAvailable)
            .limit(numberOfSeatsToReserve)
            .forEach(seat -> {
                seat.reserve(groupName);
                reservedSeatCount.getAndIncrement();
            });
        return reservedSeatCount.get();
    }

    public int tryReserveAllSeats(String groupName) {
        AtomicInteger reservedSeatCount = new AtomicInteger();

        if (isAllSeatsAreAvailable()) {
            seats.forEach(seat -> {
                seat.reserve(groupName);
                reservedSeatCount.getAndIncrement();
            });
        }

        return reservedSeatCount.get();
    }

    public boolean hasALoner() {
        return findNumberOfFreeSeats() == 1;
    }

    public boolean willCreateALonerIfTryReserve(int numberOfSeats) {
        return numberOfSeats - findNumberOfFreeSeats() == 1;
    }

    public boolean isGroupEmpty(String groupName) {
        return seats.stream()
            .filter(seat -> seat.isInGroupWithGroupName(groupName))
            .allMatch(Seat::isReserved);
    }

    public void changeAllReservationSeatsToAvailable(String groupName) {
        seats.stream()
            .filter(seat -> seat.isInGroupWithGroupName(groupName))
            .forEach(Seat::unassignCustomer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cube that = (Cube) o;
        return Objects.equals(name, that.name) && Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, seats);
    }

    private int findNumberOfFreeSeats() {
        return Math.toIntExact(seats.stream().filter(Seat::isAvailable).count());
    }

    private boolean isAllSeatsAreAvailable() {
        int numberOfAvailableSeats = (int) seats.stream()
            .filter(Seat::isAvailable)
            .count();

        return numberOfAvailableSeats == seats.size();
    }
}
