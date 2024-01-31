package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryInventoryIngredient;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryIngredientAssembler {

    public List<InMemoryInventoryIngredient> fromIngredientsToInMemoryIngredients(
        List<MenuIngredient> menuIngredients) {
        return menuIngredients.stream().map(this::createInMemoryIngredients).collect(Collectors.toList());
    }

    public List<InMemoryInventoryIngredient> fromInventoryIngredientsToInMemoryIngredients(
        List<InventoryIngredient> inventoryIngredients) {
        return inventoryIngredients.stream().map(this::createInMemoryInventoryIngredients).collect(Collectors.toList());
    }

    public List<MenuIngredient> fromInMemoryIngredientsToIngredients(
        List<InMemoryInventoryIngredient> inMemoryInventoryIngredients) {
        return inMemoryInventoryIngredients.stream().map(this::createIngredients).collect(Collectors.toList());
    }

    public List<InventoryIngredient> fromInMemoryIngredientsToInventoryIngredients(
        List<InMemoryInventoryIngredient> inMemoryInventoryIngredients) {
        return inMemoryInventoryIngredients.stream().map(this::createInventoryIngredients).collect(Collectors.toList());
    }

    private InMemoryInventoryIngredient createInMemoryIngredients(MenuIngredient menuIngredient) {
        return new InMemoryInventoryIngredient(menuIngredient.name(), menuIngredient.quantity());
    }

    private InMemoryInventoryIngredient createInMemoryInventoryIngredients(InventoryIngredient ingredient) {
        return new InMemoryInventoryIngredient(ingredient.getName(), ingredient.getQuantity());
    }

    private MenuIngredient createIngredients(InMemoryInventoryIngredient inMemoryInventoryIngredient) {
        return new MenuIngredient(inMemoryInventoryIngredient.getName(), inMemoryInventoryIngredient.getQuantity());
    }

    private InventoryIngredient createInventoryIngredients(InMemoryInventoryIngredient inMemoryInventoryIngredient) {
        return new InventoryIngredient(inMemoryInventoryIngredient.getName(),
            inMemoryInventoryIngredient.getQuantity());
    }
}
