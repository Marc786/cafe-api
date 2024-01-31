package ca.ulaval.glo4002.cafe.domain.cafe;

import static java.util.Objects.nonNull;

import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;

import java.util.List;
import java.util.Objects;

public class Cafe {

    private final String name;
    private final CubeList cubes;
    private final CustomerList customers;
    private final ReservationList reservations;
    private final ReservationStrategy reservationStrategy;
    private final MenuItemList menuItemList;
    private final Biller biller;
    private final Inventory inventory;

    public Cafe(String name, CubeList cubes, CustomerList customers, ReservationList reservations,
        ReservationStrategy reservationStrategy, MenuItemList menuItemList, Biller biller, Inventory inventory) {
        this.name = name;
        this.cubes = cubes;
        this.customers = customers;
        this.reservations = reservations;
        this.reservationStrategy = reservationStrategy;
        this.menuItemList = menuItemList;
        this.biller = biller;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public List<Cube> getCubes() {
        return cubes.getCubes();
    }

    public List<Reservation> getReservations() {
        return reservations.getReservations();
    }

    public List<Customer> getCustomersList() {
        return customers.getCustomers();
    }

    public ReservationStrategy getReservationStrategy() {
        return reservationStrategy;
    }

    public Biller getBiller() {
        return biller;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addCustomer(Customer customer) {
        cubes.assignCustomerToFirstFreeSeat(customer.getId());
        customers.add(customer);
    }

    public void addCustomerInGroup(Customer customer, String groupName) {
        cubes.assignCustomerToReservationFirstFreeSeat(customer.getId(), groupName);
        customers.add(customer);
    }

    public boolean isCustomerAlreadyInside(CustomerId customerId) {
        return customers.isCustomerAlreadyInside(customerId);
    }

    public Seat findCustomerSeat(CustomerId customerId) {
        return cubes.findCustomerSeat(customerId);
    }

    public Customer findCustomerById(CustomerId customerId) {
        return customers.findCustomerById(customerId);
    }

    public boolean doesReservationAlreadyExist(String groupName) {
        return reservations.doesReservationAlreadyExist(groupName);
    }

    public void addReservation(Reservation reservation) {
        reservationStrategy.reserve(cubes.getCubes(), reservation);
        reservations.add(reservation);
    }

    public void close() {
        cubes.setAllSeatsAvailable();
        customers.clear();
        reservations.clear();
        inventory.reset();
    }

    public List<MenuItem> getMenuItems() {
        return menuItemList.getMenuItems();
    }

    public void addOrders(CustomerId customerId, List<String> orders) {
        List<MenuItem> menuItems = menuItemList.findMenuItems(orders);
        customers.addOrders(customerId, menuItems);
        inventory.decrease(convertMenuItemsToIngredients(menuItems));
    }

    public void checkoutCustomer(CustomerId customerId) {
        String groupName = cubes.findCustomerSeat(customerId).getGroupName();
        if (cubes.unassignCustomerIsLastInGroup(customerId, groupName)) {
            reservations.remove(groupName);
        }

        customers.createBill(customerId, isInGroup(groupName), biller);
    }

    public void addToInventory(List<MenuIngredient> menuIngredients) {
        inventory.add(menuIngredients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cafe cafe = (Cafe) o;
        return Objects.equals(name, cafe.name) && Objects.equals(getCubes(), cafe.getCubes())
            && Objects.equals(customers, cafe.customers) && Objects.equals(reservations,
            cafe.reservations) && Objects.equals(reservationStrategy.getClass(), cafe.reservationStrategy.getClass())
            && Objects.equals(menuItemList, cafe.menuItemList) && Objects.equals(biller, cafe.biller)
            && Objects.equals(inventory, cafe.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cubes, customers, reservations, reservationStrategy, menuItemList, biller, inventory);
    }

    private List<MenuIngredient> convertMenuItemsToIngredients(List<MenuItem> menuItems) {
        return menuItems.stream().flatMap(menuItem -> menuItem.getRecette().stream()).toList();
    }

    private boolean isInGroup(String groupName) {
        return nonNull(groupName);
    }
}
