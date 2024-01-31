package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;

import java.util.List;
import java.util.Objects;

public class Inventory {

    private final List<InventoryIngredient> inventory;

    public Inventory(List<InventoryIngredient> ingredients) {
        this.inventory = ingredients;
    }

    public List<InventoryIngredient> getIngredients() {
        return inventory;
    }

    public void add(List<MenuIngredient> menuIngredients) {
        menuIngredients.forEach(this::addOneElement);
    }

    public void reset() {
        inventory.forEach(InventoryIngredient::resetQuantity);
    }

    public void decrease(List<MenuIngredient> menuIngredients) {
        menuIngredients.forEach(this::findAndDecrement);
    }

    public boolean isEmpty() {
        return inventory.stream().allMatch(ingredient -> ingredient.getQuantity() == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Inventory inventory1 = (Inventory) o;
        return Objects.equals(getIngredients(), inventory1.getIngredients());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIngredients());
    }

    private void addOneElement(MenuIngredient menuIngredient) {
        inventory.stream().filter(inventoryIngredient -> inventoryIngredient.getName().equals(menuIngredient.name()))
            .findFirst().ifPresent(foundIngredient -> foundIngredient.addQuantity(menuIngredient.quantity()));
    }

    private void findAndDecrement(MenuIngredient menuIngredient) {
        inventory.stream()
            .filter(inventoryIngredient -> inventoryIngredient.getName().equals(menuIngredient.name()))
            .findFirst()
            .ifPresent(foundIngredient -> foundIngredient.decrementQuantity(menuIngredient.quantity()));
    }
}
