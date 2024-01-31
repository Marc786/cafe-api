package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.customer.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CubeList {

    private final List<Cube> cubes;

    public CubeList(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public Seat findCustomerSeat(CustomerId customerId) {
        return cubes.stream()
            .map(cube -> cube.findCustomerSeat(customerId))
            .flatMap(Optional::stream)
            .findFirst()
            .orElseThrow(CustomerNotFoundException::new);
    }

    public void setAllSeatsAvailable() {
        cubes.forEach(Cube::setAvailable);
    }

    public void assignCustomerToFirstFreeSeat(CustomerId customerId) {
        if (cubes.stream().noneMatch(cube -> cube.assignCustomerIfPossible(customerId))) {
            throw new InsufficientSeatsException();
        }
    }

    public void assignCustomerToReservationFirstFreeSeat(CustomerId customerId, String groupName) {
        if (cubes.stream().noneMatch(cube -> cube.assignCustomerToGroupSeatIfPossible(customerId, groupName))) {
            throw new NoGroupSeatsException();
        }
    }

    public boolean unassignCustomerIsLastInGroup(CustomerId customerId, String groupName) {
        unassignCustomerIfPossible(customerId);
        if (isGroupEmpty(groupName)) {
            changeAllReservationSeatsToAvailable(groupName);
            return true;
        }
        return false;
    }

    private void unassignCustomerIfPossible(CustomerId customerId) {
        if (cubes.stream().noneMatch(cube -> cube.unassignCustomerIfPossible(customerId))) {
            throw new CustomerNotFoundException();
        }
    }

    private boolean isGroupEmpty(String groupName) {
        return cubes.stream().allMatch(cube -> cube.isGroupEmpty(groupName));
    }

    private void changeAllReservationSeatsToAvailable(String groupName) {
        cubes.forEach(cube -> cube.changeAllReservationSeatsToAvailable(groupName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CubeList cubeList = (CubeList) o;
        return Objects.equals(cubes, cubeList.cubes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cubes);
    }
}
