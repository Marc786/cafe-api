package ca.ulaval.glo4002.cafe.domain.cafe.config;

import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CafeConfig {

    private final ReservationStrategy reservationStrategy;
    private final String organizationName;
    private final int cubeSize;
    private final Rate countryRate;
    private final Rate stateRate;
    private final Rate tipRate;
    private final List<String> cubeNames = Stream.of("Bloom", "Merryweather", "Tinker Bell", "Wanda").sorted().toList();
    private final List<MenuItem> menuItems = createMenuItems();
    private final List<InventoryIngredient> inventoryIngredients = createBasicInventoryIngredients();

    public CafeConfig(ReservationStrategy reservationStrategy, String organizationName, int cubeSize, Rate countryRate,
        Rate stateRate, Rate tipRate) {
        this.reservationStrategy = reservationStrategy;
        this.organizationName = organizationName;
        this.cubeSize = cubeSize;
        this.countryRate = countryRate;
        this.stateRate = stateRate;
        this.tipRate = tipRate;
    }

    public ReservationStrategy getReservationStrategy() {
        return reservationStrategy;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public int getCubeSize() {
        return cubeSize;
    }

    public List<String> getCubeNames() {
        return cubeNames;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Rate getCountryRate() {
        return countryRate;
    }

    public Rate getStateRate() {
        return stateRate;
    }

    public Rate getTipRate() {
        return tipRate;
    }

    public List<InventoryIngredient> getInventoryIngredients() {
        return inventoryIngredients;
    }

    private List<InventoryIngredient> createBasicInventoryIngredients() {
        List<InventoryIngredient> inventory = new ArrayList<>();
        inventory.add(new InventoryIngredient("Chocolate", 0));
        inventory.add(new InventoryIngredient("Espresso", 0));
        inventory.add(new InventoryIngredient("Milk", 0));
        inventory.add(new InventoryIngredient("Water", 0));
        return inventory;
    }

    private List<MenuItem> createMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Americano", new Price(2.25), createRecette(50, 50, 0, 0)));
        menuItems.add(new MenuItem("Dark Roast", new Price(2.10), createRecette(40, 40, 10, 10)));
        menuItems.add(new MenuItem("Cappuccino", new Price(3.29), createRecette(50, 40, 0, 10)));
        menuItems.add(new MenuItem("Espresso", new Price(2.95), createRecette(60, 0, 0, 0)));
        menuItems.add(new MenuItem("Flat White", new Price(3.75), createRecette(50, 0, 0, 50)));
        menuItems.add(new MenuItem("Latte", new Price(2.95), createRecette(50, 0, 0, 50)));
        menuItems.add(new MenuItem("Macchiato", new Price(4.75), createRecette(80, 0, 0, 20)));
        menuItems.add(new MenuItem("Mocha", new Price(4.15), createRecette(50, 0, 10, 40)));
        return menuItems;
    }

    private List<MenuIngredient> createRecette(int quantityEspresso, int quantityWater, int quantityChoco,
        int quantityMilk) {
        List<MenuIngredient> recette = new ArrayList<>();

        if (quantityEspresso > 0)
            recette.add(new MenuIngredient("Espresso", quantityEspresso));
        if (quantityWater > 0)
            recette.add(new MenuIngredient("Water", quantityWater));
        if (quantityChoco > 0)
            recette.add(new MenuIngredient("Chocolate", quantityChoco));
        if (quantityMilk > 0)
            recette.add(new MenuIngredient("Milk", quantityMilk));

        return recette;
    }
}
