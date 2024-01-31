package ca.ulaval.glo4002.cafe.api.rest.cafe;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeAssembler;
import ca.ulaval.glo4002.cafe.api.rest.cube.CubeResponse;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;

import java.util.List;

public class CafeAssembler {

    private final CubeAssembler cubeAssembler = new CubeAssembler();

    public CafeResponse toCafeResponse(CafeDTO cafe) {
        List<CubeResponse> cubeResponses = cubeAssembler.toCubeResponses(cafe.cubes());

        return new CafeResponse(cafe.name(), cubeResponses);
    }
}
