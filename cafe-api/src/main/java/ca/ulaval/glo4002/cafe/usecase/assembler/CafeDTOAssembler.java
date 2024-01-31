package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;

public class CafeDTOAssembler {

    private final CubeDTOAssembler cubeDTOAssembler = new CubeDTOAssembler();

    public CafeDTO cafeToCafeDTO(Cafe cafe) {
        return new CafeDTO(cafe.getName(), cubeDTOAssembler.fromCubesToCubesDTO(cafe.getCubes()));
    }
}
