package ca.ulaval.glo4002.cafe.domain.customer;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.exception.NoBillException;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CustomerTest {

    private static final int A_PRICE = 5;
    private static final CustomerId CUSTOMER_ID = new CustomerId("123");
    private static final String CUSTOMER_NAME = "Joblo";
    private static final MenuItem LATTE = new MenuItem("Latte", new Price(A_PRICE), new ArrayList<>());
    private static final MenuItem ESPRESSO = new MenuItem("Espresso", new Price(A_PRICE), new ArrayList<>());
    private static final boolean ANY_GROUP_STATUS = false;

    private final Biller billerMock = mock(Biller.class);

    @Test
    void nonexistentBill_tryGetBill_throwsNoBillException() {
        Customer customerWithoutBill = new Customer(CUSTOMER_ID, CUSTOMER_NAME, new ArrayList<>());
        assertThrows(NoBillException.class, customerWithoutBill::tryGetBill);
    }

    @Test
    void existentBill_tryGetBill_returnBill() {
        Bill bill = new Bill(new Price(A_PRICE), new Price(A_PRICE), new Price(A_PRICE), new Price(A_PRICE));
        Customer customerWithBill = new Customer(CUSTOMER_ID, CUSTOMER_NAME, new ArrayList<>(), bill);

        Bill returnedBill = customerWithBill.tryGetBill();

        assertThat(returnedBill).isEqualTo(bill);
    }

    @Test
    void addOrders_menuItemsAreAdded() {
        List<MenuItem> expectedMenuItems = List.of(ESPRESSO, LATTE);
        List<MenuItem> order = List.of(LATTE);
        Customer customer = createCustomer();

        customer.addOrders(order);

        assertThat(customer.getMenuItemsOrdered()).isEqualTo(expectedMenuItems);
    }

    @Test
    void createBill_setCustomerBill() {
        Bill expectedBill = new Bill(new Price(A_PRICE), new Price(A_PRICE), new Price(A_PRICE), new Price(A_PRICE));
        Customer customer = createCustomer();
        List<MenuItem> customerOrder = customer.getMenuItemsOrdered();
        when(billerMock.createBill(customerOrder, ANY_GROUP_STATUS)).thenReturn(expectedBill);

        customer.createBill(billerMock, ANY_GROUP_STATUS);

        assertThat(customer.getBill()).isEqualTo(expectedBill);
    }

    @Test
    void createBill_createBillIsCalled() {
        Customer customer = createCustomer();
        List<MenuItem> customerOrder = customer.getMenuItemsOrdered();

        customer.createBill(billerMock, ANY_GROUP_STATUS);

        verify(billerMock).createBill(customerOrder, ANY_GROUP_STATUS);
    }

    private Customer createCustomer() {
        List<MenuItem> customerMenuItems = new ArrayList<>();
        customerMenuItems.add(ESPRESSO);
        return new Customer(CUSTOMER_ID, CUSTOMER_NAME, customerMenuItems);
    }
}
