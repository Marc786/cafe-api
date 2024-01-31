package ca.ulaval.glo4002.cafe.domain.customer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.cafe.domain.customer.exception.CustomerNotFoundException;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CustomerListTest {

    private static final String CUSTOMER_NAME = "nom";
    private static final int PRICE = 5;
    private static final String MENU_ITEM_NAME = "Latte";
    private static final boolean IS_IN_GROUP = true;
    private final Rate COUNTRY_RATE = new Rate(0);
    private final Rate STATE_RATE = new Rate(0);
    private final Rate TIP_RATE = new Rate(0);
    private final CustomerId CUSTOMER_ID = new CustomerId("123");
    private final CustomerId NON_EXISTENT_CUSTOMER_ID = new CustomerId("456");
    private final List<MenuItem> MENU_ITEMS = new ArrayList<>();
    private final Customer customerMock = mock(Customer.class);
    private CustomerList customerList;
    private Customer customer;

    @BeforeEach
    void createCustomerList() {
        customerList = new CustomerList();
        customer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, MENU_ITEMS);
        customerList.add(customer);
    }

    @Test
    void aCustomer_addCustomer_customerIsAdded() {
        assertThat(customer).isEqualTo(customerList.findCustomerById(CUSTOMER_ID));
    }

    @Test
    void emptyCustomerList_findCustomerById_throwsCustomerNotFoundException() {
        CustomerList emptyCustomerList = new CustomerList();
        assertThrows(CustomerNotFoundException.class, () -> emptyCustomerList.findCustomerById(CUSTOMER_ID));
    }

    @Test
    void customerNotInside_findCustomerById_throwsCustomerNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> customerList.findCustomerById(NON_EXISTENT_CUSTOMER_ID));
    }

    @Test
    void customerInside_findCustomerById_returnCustomer() {
        Customer customerFound = customerList.findCustomerById(CUSTOMER_ID);

        assertThat(customerFound).isEqualTo(customer);
    }

    @Test
    void customerInside_isCustomerAlreadyInside_customerIsInside() {
        boolean customerFound = customerList.isCustomerAlreadyInside(CUSTOMER_ID);

        assertThat(customerFound).isTrue();
    }

    @Test
    void customerNotInside_isCustomerAlreadyInside_customerIsNotInside() {
        boolean customerFound = customerList.isCustomerAlreadyInside(NON_EXISTENT_CUSTOMER_ID);

        assertThat(customerFound).isFalse();
    }

    @Test
    void customerInside_clearCustomer_shouldBeEmpty() {
        customerList.clear();

        assertTrue(customerList.isEmpty());
    }

    @Test
    void addOrders_addMenuItemsToCustomerOrder() {
        List<MenuItem> menuItems = createMenuItems();

        customerList.addOrders(customer.getId(), menuItems);

        assertThat(customer.getMenuItemsOrdered()).isEqualTo(menuItems);
    }

    @Test
    void customer_createBill_customerBillIsNoLongerNull() {
        Biller biller = createBiller();

        customerList.createBill(CUSTOMER_ID, IS_IN_GROUP, biller);

        assertThat(customer.tryGetBill()).isNotNull();
    }

    private Biller createBiller() {
        return new Biller(COUNTRY_RATE, STATE_RATE, TIP_RATE);
    }

    private List<MenuItem> createMenuItems() {
        return List.of(new MenuItem(MENU_ITEM_NAME, new Price(PRICE), new ArrayList<>()));
    }
}
