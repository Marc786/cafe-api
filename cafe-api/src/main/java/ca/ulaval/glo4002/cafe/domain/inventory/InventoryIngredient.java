package ca.ulaval.glo4002.cafe.domain.inventory;

import ca.ulaval.glo4002.cafe.domain.inventory.exception.InsufficientIngredientException;

import java.util.Objects;

public class InventoryIngredient {

    private final String name;
    private int quantity;

    public InventoryIngredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void resetQuantity() {
        this.quantity = 0;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrementQuantity(int quantity) {
        if (this.quantity - quantity < 0)
            throw new InsufficientIngredientException();

        this.quantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InventoryIngredient that = (InventoryIngredient) o;
        return quantity == that.quantity && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }
}
