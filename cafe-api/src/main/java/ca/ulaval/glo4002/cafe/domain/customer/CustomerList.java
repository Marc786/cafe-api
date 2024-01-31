package ca.ulaval.glo4002.cafe.domain.customer;

import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerList {

    private final List<Customer> customers;

    public CustomerList() {
        this.customers = new ArrayList<>();
    }

    public CustomerList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Customer findCustomerById(CustomerId customerId) {
        return customers.stream()
            .filter(customer -> customer.getId().equals(customerId))
            .findFirst()
            .orElseThrow(CustomerNotFoundException::new);
    }

    public boolean isCustomerAlreadyInside(CustomerId customerId) {
        return customers.stream().anyMatch(customer -> customer.getId().equals(customerId));
    }

    public void addOrders(CustomerId customerId, List<MenuItem> orders) {
        Customer customer = findCustomerById(customerId);
        customer.addOrders(orders);
    }

    public void createBill(CustomerId id, boolean isInGroup, Biller biller) {
        Customer customer = findCustomerById(id);
        customer.createBill(biller, isInGroup);
    }

    public void clear() {
        customers.clear();
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerList that = (CustomerList) o;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customers);
    }
}
