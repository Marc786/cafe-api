package ca.ulaval.glo4002.cafe.api.rest.inventory;

import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InventoryAssembler {

    public Map<String, Integer> fromIngredientsDTOtoInventoryResponse(List<IngredientDTO> ingredientDTOs) {
        return ingredientDTOs.stream().collect(
            Collectors.toMap(IngredientDTO::getName, IngredientDTO::getQuantity, Math::addExact, TreeMap::new));
    }

    public List<MenuIngredient> fromInventoryRequestToIngredients(AddInventoryRequest addInventoryRequest) {
        List<MenuIngredient> menuIngredients = new ArrayList<>();
        menuIngredients.add(new MenuIngredient("Chocolate", addInventoryRequest.chocolateQuantity()));
        menuIngredients.add(new MenuIngredient("Espresso", addInventoryRequest.espressoQuantity()));
        menuIngredients.add(new MenuIngredient("Milk", addInventoryRequest.milkQuantity()));
        menuIngredients.add(new MenuIngredient("Water", addInventoryRequest.waterQuantity()));

        return menuIngredients;
    }
}
