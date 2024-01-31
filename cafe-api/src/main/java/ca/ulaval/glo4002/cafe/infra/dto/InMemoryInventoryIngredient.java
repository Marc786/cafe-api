package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.Objects;

public class InMemoryInventoryIngredient {

    private final String name;
    private final int quantity;

    public InMemoryInventoryIngredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryInventoryIngredient that = (InMemoryInventoryIngredient) o;
        return getQuantity() == that.getQuantity() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity());
    }
}
