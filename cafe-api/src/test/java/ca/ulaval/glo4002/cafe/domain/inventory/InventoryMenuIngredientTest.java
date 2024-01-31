package ca.ulaval.glo4002.cafe.domain.inventory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.inventory.exception.InsufficientIngredientException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryMenuIngredientTest {

    private InventoryIngredient inventoryIngredient;

    @BeforeEach
    void createIngredient() {
        inventoryIngredient = new InventoryIngredient("Milk", 3);
    }

    @Test
    void resetQuantity_quantityIsSetToZero() {
        inventoryIngredient.resetQuantity();

        assertThat(inventoryIngredient.getQuantity()).isEqualTo(0);
    }

    @Test
    void addQuantity_quantityIsAdded() {
        int expectedQuantity = 5;
        int incrementQuantity = 2;

        inventoryIngredient.addQuantity(incrementQuantity);

        assertThat(inventoryIngredient.getQuantity()).isEqualTo(expectedQuantity);
    }

    @Test
    void quantityIsGreaterThanDecrementQuantity_decrementQuantity_quantityIsDecremented() {
        int expectedQuantity = 2;
        int decrementQuantity = 1;

        inventoryIngredient.decrementQuantity(decrementQuantity);

        assertThat(inventoryIngredient.getQuantity()).isEqualTo(expectedQuantity);
    }

    @Test
    void quantityIsSmallerThanDecrementQuantity_decrementQuantity_shouldThrowInsufficientIngredientException() {
        int decrementQuantity = 4;

        assertThrows(InsufficientIngredientException.class,
            () -> inventoryIngredient.decrementQuantity(decrementQuantity));
    }
}
