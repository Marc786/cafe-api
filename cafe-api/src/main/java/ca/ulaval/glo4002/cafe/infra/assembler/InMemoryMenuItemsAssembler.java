package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryInventoryIngredient;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryMenuItem;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryMenuItemsAssembler {

    private final InMemoryIngredientAssembler inMemoryIngredientAssembler = new InMemoryIngredientAssembler();

    public List<InMemoryMenuItem> fromMenuItemsToInMemoryMenuItems(List<MenuItem> menuItems) {
        return menuItems.stream()
            .map(menuItem -> new InMemoryMenuItem(menuItem.getName(), menuItem.getPrice().getDoubleValue(),
                createInMemoryIngredients(menuItem.getRecette())))
            .collect(Collectors.toList());
    }

    public List<MenuItem> fromInMemoryMenusItemToMenuItems(List<InMemoryMenuItem> inMemoryMenuItems) {
        return inMemoryMenuItems.stream()
            .map(inMemoryMenuItem -> new MenuItem(inMemoryMenuItem.getName(), new Price(inMemoryMenuItem.getPrice()),
                createIngredients(inMemoryMenuItem.getRecette())))
            .collect(Collectors.toList());
    }

    private List<InMemoryInventoryIngredient> createInMemoryIngredients(List<MenuIngredient> menuIngredients) {
        return inMemoryIngredientAssembler.fromIngredientsToInMemoryIngredients(menuIngredients);
    }

    private List<MenuIngredient> createIngredients(List<InMemoryInventoryIngredient> inMemoryInventoryIngredients) {
        return inMemoryIngredientAssembler.fromInMemoryIngredientsToIngredients(inMemoryInventoryIngredients);
    }
}
