package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.usecase.assembler.CafeDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;

public class FindCafe {

    private final CafeRepository cafeRepository;
    private final CafeDTOAssembler cafeAssembler;

    public FindCafe(CafeRepository cafeRepository, CafeDTOAssembler cafeAssembler) {
        this.cafeRepository = cafeRepository;
        this.cafeAssembler = cafeAssembler;
    }

    public CafeDTO find() {
        Cafe cafe = cafeRepository.fetchCafe();

        return cafeAssembler.cafeToCafeDTO(cafe);
    }
}
