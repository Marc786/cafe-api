package ca.ulaval.glo4002.cafe.api.rest.cube;

import ca.ulaval.glo4002.cafe.api.rest.seat.SeatAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.CubeDTO;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CubeAssembler {

    private final SeatAssembler seatAssembler = new SeatAssembler();

    public List<CubeResponse> toCubeResponses(List<CubeDTO> cubes) {
        return cubes.stream().map(this::createCubeResponse).sorted(Comparator.comparing(CubeResponse::name))
            .collect(Collectors.toList());
    }

    private CubeResponse createCubeResponse(CubeDTO cube) {
        return new CubeResponse(cube.name(), seatAssembler.toSeatsResponse(cube.seats()));
    }
}
