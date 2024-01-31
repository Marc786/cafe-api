package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.ArrayList;

public class CustomerFactory {

    public Customer create(CustomerId id, String name) {
        return new Customer(id, name, new ArrayList<>());
    }
}
