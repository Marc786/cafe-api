package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryCube;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryCubeAssembler {

    private final InMemorySeatAssembler inMemorySeatAssembler = new InMemorySeatAssembler();

    public List<InMemoryCube> fromCubesToInMemoryCubes(List<Cube> cubes) {
        return cubes.stream().map(this::createInMemoryCube).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Cube> fromInMemoryCubesToCubes(List<InMemoryCube> inMemoryCubes) {
        return inMemoryCubes.stream().map(this::createCube).collect(Collectors.toCollection(ArrayList::new));
    }

    private Cube createCube(InMemoryCube inMemoryCube) {
        return new Cube(inMemoryCube.getName(),
            inMemorySeatAssembler.fromInMemorySeatsToSeats(inMemoryCube.getInMemorySeats()));
    }

    private InMemoryCube createInMemoryCube(Cube cube) {
        return new InMemoryCube(cube.getName(),
            inMemorySeatAssembler.fromSeatsToInMemorySeats(cube.getSeats()));
    }
}
