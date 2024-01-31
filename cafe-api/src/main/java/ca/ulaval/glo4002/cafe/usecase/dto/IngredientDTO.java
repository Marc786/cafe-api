package ca.ulaval.glo4002.cafe.usecase.dto;

import java.util.Objects;

public class IngredientDTO {

    private final String name;
    private final int quantity;

    public IngredientDTO(String name, int quantity) {
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
        IngredientDTO that = (IngredientDTO) o;
        return getQuantity() == that.getQuantity() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getQuantity());
    }
}
