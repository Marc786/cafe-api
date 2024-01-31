package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.List;
import java.util.Objects;

public class InMemoryCustomer {

    private final String id;
    private final String name;
    private final List<InMemoryMenuItem> menuItemsOrdered;
    private final InMemoryBill inMemoryBill;

    public InMemoryCustomer(String id, String name, List<InMemoryMenuItem> menuItemsOrdered,
        InMemoryBill inMemoryBill) {
        this.id = id;
        this.name = name;
        this.menuItemsOrdered = menuItemsOrdered;
        this.inMemoryBill = inMemoryBill;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<InMemoryMenuItem> getMenuItemsOrdered() {
        return menuItemsOrdered;
    }

    public InMemoryBill getInMemoryBill() {
        return inMemoryBill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryCustomer that = (InMemoryCustomer) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
            && Objects.equals(menuItemsOrdered, that.menuItemsOrdered) && Objects.equals(inMemoryBill,
            that.inMemoryBill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, menuItemsOrdered, inMemoryBill);
    }
}
