package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.List;
import java.util.Objects;

public class InMemoryMenuItem {

    private final String name;
    private final double price;
    private final List<InMemoryInventoryIngredient> recette;

    public InMemoryMenuItem(String name, double price, List<InMemoryInventoryIngredient> recette) {
        this.name = name;
        this.price = price;
        this.recette = recette;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<InMemoryInventoryIngredient> getRecette() {
        return recette;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryMenuItem that = (InMemoryMenuItem) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 && Objects.equals(getName(), that.getName())
            && Objects.equals(getRecette(), that.getRecette());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getRecette());
    }
}
