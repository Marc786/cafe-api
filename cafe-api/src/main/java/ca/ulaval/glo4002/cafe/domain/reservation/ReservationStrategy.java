package ca.ulaval.glo4002.cafe.domain.reservation;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;

import java.util.List;

public interface ReservationStrategy {

    void reserve(List<Cube> cubes, Reservation reservation);
}
