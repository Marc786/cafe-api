package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.usecase.assembler.IngredientDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;

import java.util.List;

public class FindInventory {


    private final CafeRepository cafeRepository;
    private final IngredientDTOAssembler ingredientDTOAssembler;

    public FindInventory(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
        this.ingredientDTOAssembler = new IngredientDTOAssembler();
    }

    public List<IngredientDTO> find() {
        Cafe cafe = cafeRepository.fetchCafe();

        return ingredientDTOAssembler.fromInventoryToIngredientsDTO(cafe.getInventory());
    }
}
