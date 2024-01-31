package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;

import java.util.List;

public class IngredientDTOAssembler {

    public List<IngredientDTO> fromInventoryToIngredientsDTO(Inventory inventory) {
        return inventory.getIngredients().stream().map(this::createIngredientDTO).toList();
    }

    private IngredientDTO createIngredientDTO(InventoryIngredient ingredient) {
        return new IngredientDTO(ingredient.getName(), ingredient.getQuantity());
    }
}
