package ca.ulaval.glo4002.cafe.domain.menu;

import java.util.List;
import java.util.Objects;

public class MenuItem {

    private final String name;
    private final Price price;
    private final List<MenuIngredient> recette;

    public MenuItem(String name, Price price, List<MenuIngredient> recette) {
        this.name = name;
        this.price = price;
        this.recette = recette;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public List<MenuIngredient> getRecette() {
        return recette;
    }

    public boolean hasName(String name) {
        return name.equals(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MenuItem item = (MenuItem) o;
        return Objects.equals(getName(), item.getName()) && Objects.equals(getPrice(), item.getPrice())
            && Objects.equals(getRecette(), item.getRecette());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getRecette());
    }
}
