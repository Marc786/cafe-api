package ca.ulaval.glo4002.cafe.domain.customer;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CustomerFactoryTest {

    private static final CustomerId CUSTOMER_ID = new CustomerId("ANID");
    private static final String CUSTOMER_NAME = "ANAME";
    private static final List<MenuItem> MENU_ITEMS = new ArrayList<>();

    @Test
    void createCustomer_returnsExpectedCustomer() {
        CustomerFactory customerFactory = new CustomerFactory();
        Customer expectedCustomer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, MENU_ITEMS);

        Customer customer = customerFactory.create(CUSTOMER_ID, CUSTOMER_NAME);

        assertThat(customer).isEqualTo(expectedCustomer);
    }
}
