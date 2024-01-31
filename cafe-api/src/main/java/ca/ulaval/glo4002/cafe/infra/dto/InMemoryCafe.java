package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.List;
import java.util.Objects;

public class InMemoryCafe {

    private final String name;
    private final List<InMemoryCube> inMemoryCubes;
    private final List<InMemoryCustomer> inMemoryCustomers;
    private final List<InMemoryReservation> inMemoryReservations;
    private final InMemoryStrategy inMemoryStrategy;
    private final List<InMemoryMenuItem> inMemoryMenuItems;
    private final InMemoryRate inMemoryCountryRate;
    private final InMemoryRate inMemoryStateRate;

    private final InMemoryRate inMemoryTipRate;
    private final List<InMemoryInventoryIngredient> inMemoryInventory;

    public InMemoryCafe(String name, List<InMemoryCube> inMemoryCubes, List<InMemoryCustomer> inMemoryCustomers,
        List<InMemoryReservation> inMemoryReservations, InMemoryStrategy inMemoryStrategy,
        List<InMemoryMenuItem> inMemoryMenuItems, InMemoryRate inMemoryCountryRate,
        InMemoryRate inMemoryStateRate, List<InMemoryInventoryIngredient> inMemoryInventory,
        InMemoryRate inMemoryTipRate) {
        this.name = name;
        this.inMemoryCubes = inMemoryCubes;
        this.inMemoryCustomers = inMemoryCustomers;
        this.inMemoryReservations = inMemoryReservations;
        this.inMemoryStrategy = inMemoryStrategy;
        this.inMemoryMenuItems = inMemoryMenuItems;
        this.inMemoryCountryRate = inMemoryCountryRate;
        this.inMemoryStateRate = inMemoryStateRate;
        this.inMemoryInventory = inMemoryInventory;
        this.inMemoryTipRate = inMemoryTipRate;
    }

    public String getName() {
        return name;
    }

    public List<InMemoryCube> getInMemoryCubes() {
        return inMemoryCubes;
    }

    public List<InMemoryCustomer> getInMemoryCustomers() {
        return inMemoryCustomers;
    }

    public List<InMemoryReservation> getInMemoryReservations() {
        return inMemoryReservations;
    }

    public InMemoryStrategy getInMemoryStrategy() {
        return inMemoryStrategy;
    }

    public List<InMemoryMenuItem> getInMemoryMenuItems() {
        return inMemoryMenuItems;
    }

    public InMemoryRate getInMemoryCountryRate() {
        return inMemoryCountryRate;
    }

    public InMemoryRate getInMemoryStateRate() {
        return inMemoryStateRate;
    }

    public List<InMemoryInventoryIngredient> getInMemoryInventory() {
        return inMemoryInventory;
    }

    public InMemoryRate getInMemoryTipRate() {
        return inMemoryTipRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryCafe that = (InMemoryCafe) o;
        return Objects.equals(name, that.name) && Objects.equals(inMemoryCubes, that.inMemoryCubes)
            && Objects.equals(inMemoryCustomers, that.inMemoryCustomers) && Objects.equals(
            inMemoryReservations, that.inMemoryReservations) && Objects.equals(inMemoryStrategy,
            that.inMemoryStrategy) && Objects.equals(inMemoryMenuItems, that.inMemoryMenuItems)
            && Objects.equals(inMemoryCountryRate, that.inMemoryCountryRate) && Objects.equals(
            inMemoryStateRate, that.inMemoryStateRate) && Objects.equals(inMemoryTipRate, that.inMemoryTipRate)
            && Objects.equals(inMemoryInventory, that.inMemoryInventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inMemoryCubes, inMemoryCustomers, inMemoryReservations, inMemoryStrategy,
            inMemoryMenuItems, inMemoryCountryRate, inMemoryStateRate, inMemoryTipRate, inMemoryInventory);
    }
}
