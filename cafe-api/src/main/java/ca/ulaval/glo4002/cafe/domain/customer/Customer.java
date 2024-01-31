package ca.ulaval.glo4002.cafe.domain.customer;

import static java.util.Objects.isNull;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.exception.NoBillException;

import java.util.List;
import java.util.Objects;

public class Customer {

    private final CustomerId id;
    private final String name;
    private final List<MenuItem> menuItemsOrdered;
    private Bill bill;

    public Customer(CustomerId id, String name, List<MenuItem> menuItemsOrdered) {
        this.id = id;
        this.name = name;
        this.menuItemsOrdered = menuItemsOrdered;
        this.bill = null;
    }

    public Customer(CustomerId id, String name, List<MenuItem> menuItemsOrdered, Bill bill) {
        this.id = id;
        this.name = name;
        this.menuItemsOrdered = menuItemsOrdered;
        this.bill = bill;
    }

    public CustomerId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Bill getBill() {
        return bill;
    }

    public Bill tryGetBill() {
        if (isNull(bill))
            throw new NoBillException();
        return bill;
    }

    public void addOrders(List<MenuItem> menuItems) {
        this.menuItemsOrdered.addAll(menuItems);
    }

    public List<MenuItem> getMenuItemsOrdered() {
        return menuItemsOrdered;
    }

    public void createBill(Biller biller, boolean isInGroup) {
        bill = biller.createBill(menuItemsOrdered, isInGroup);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name)
            && Objects.equals(menuItemsOrdered, customer.menuItemsOrdered) && Objects.equals(bill,
            customer.bill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, menuItemsOrdered, bill);
    }
}
