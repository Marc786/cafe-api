package ca.ulaval.glo4002.cafe.domain.cube;

import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CubeFactory {

    public List<Cube> createCubes(int cubeSize, List<String> cubeNames) {
        return IntStream.range(0, cubeNames.size()).sorted()
            .mapToObj(index -> create(cubeNames.get(index), index, cubeSize))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    private Cube create(String name, int index, int size) {
        List<Seat> cubeSeats = generateSeats(index, size);
        return new Cube(name, cubeSeats);
    }

    private List<Seat> generateSeats(int index, int cubeSize) {
        int initialSeatNumber = index * cubeSize + 1;
        int upperBoundSeatNumber = initialSeatNumber + cubeSize;
        return IntStream.range(initialSeatNumber, upperBoundSeatNumber)
            .mapToObj(seatNumber -> new Seat(new SeatId(seatNumber), Status.AVAILABLE))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
