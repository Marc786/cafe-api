package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.usecase.dto.CubeDTO;

import java.util.List;

public class CubeDTOAssembler {

    private final SeatDTOAssembler seatDTOAssembler = new SeatDTOAssembler();

    public List<CubeDTO> fromCubesToCubesDTO(List<Cube> cubes) {
        return cubes.stream().map(this::createCubeDTO).toList();
    }

    private CubeDTO createCubeDTO(Cube cube) {
        return new CubeDTO(cube.getName(), seatDTOAssembler.fromSeatToSeatsDTO(cube.getSeats()));
    }
}
