package ca.ulaval.glo4002.cafe.domain.inventory;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InventoryTest {

    private final MenuIngredient MenuINGREDIENT1 = new MenuIngredient("Chocolate", 2);
    private final MenuIngredient MenuINGREDIENT2 = new MenuIngredient("Espresso", 3);
    private final InventoryIngredient INVENTORY_INGREDIENT1 = new InventoryIngredient("Chocolate", 0);
    private final InventoryIngredient INVENTORY_INGREDIENT2 = new InventoryIngredient("Espresso", 0);

    private Inventory inventory;
    private List<MenuIngredient> menuIngredients;

    @BeforeEach
    void setup() {
        inventory = new Inventory(List.of(INVENTORY_INGREDIENT1, INVENTORY_INGREDIENT2));
        menuIngredients = createIngredients();
    }

    @Test
    void createDefaultInventory_inventoryIsEmpty() {
        Inventory emptyInventory = new Inventory(new ArrayList<>());

        assertThat(emptyInventory.isEmpty()).isTrue();
    }

    @Test
    void aListOfMultipleIngredients_add_ingredientsAreCorrectlyIncremented() {
        Inventory expectedInventory = createExpectedInventory();

        inventory.add(menuIngredients);

        assertThat(inventory).isEqualTo(expectedInventory);
    }

    @Test
    void noneExistentIngredient_add_ingredientIsIgnored() {
        MenuIngredient nonexistentMenuIngredient = new MenuIngredient("IDontExist", 1);
        Inventory expectedInventory = new Inventory(List.of(INVENTORY_INGREDIENT1, INVENTORY_INGREDIENT2));

        inventory.add(List.of(nonexistentMenuIngredient));

        assertThat(inventory).isEqualTo(expectedInventory);
    }

    @Test
    void reset_ingredientsQuantitiesAreResetToZero() {
        inventory.add(menuIngredients);

        inventory.reset();

        assertThat(inventory.isEmpty()).isTrue();
    }

    @Test
    void decrease_eachIngredientAreDecreased() {
        Inventory expectedInventory = createExpectedInventory();
        inventory.add(menuIngredients);
        inventory.add(menuIngredients);

        inventory.decrease(menuIngredients);

        assertThat(inventory).isEqualTo(expectedInventory);
    }

    @Test
    void emptyInventory_isEmpty_inventoryIsEmpty() {
        boolean isInventoryEmpty = inventory.isEmpty();

        assertThat(isInventoryEmpty).isTrue();
    }

    @Test
    void notEmptyInventory_isEmpty_inventoryIsNotEmpty() {
        inventory.add(menuIngredients);

        boolean isInventoryEmpty = inventory.isEmpty();

        assertThat(isInventoryEmpty).isFalse();
    }

    private Inventory createExpectedInventory() {
        InventoryIngredient ingredientExpected1 = new InventoryIngredient("Chocolate", 2);
        InventoryIngredient ingredientExpected2 = new InventoryIngredient("Espresso", 3);
        return new Inventory(List.of(ingredientExpected1, ingredientExpected2));
    }

    private List<MenuIngredient> createIngredients() {
        List<MenuIngredient> menuIngredients = new ArrayList<>();
        menuIngredients.add(MenuINGREDIENT1);
        menuIngredients.add(MenuINGREDIENT2);
        return menuIngredients;
    }
}
