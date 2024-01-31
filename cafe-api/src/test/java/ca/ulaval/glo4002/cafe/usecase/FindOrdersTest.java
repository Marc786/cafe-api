package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.usecase.assembler.OrderDTOAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FindOrdersTest {

    private static final String CUSTOMER_ID = "totoID";
    private static final String ITEM_NAME_1 = "Mocha";
    private static final double PRICE_1 = 2.80;
    private static final String ITEM_NAME_2 = "Latte";
    private static final double PRICE_2 = 3.25;
    private final CafeRepository cafeRepository = mock(CafeRepository.class);
    private final OrderDTOAssembler orderDTOAssembler = mock(OrderDTOAssembler.class);
    private final Cafe cafe = mock(Cafe.class);
    private final Customer customer = mock(Customer.class);
    private FindOrders findOrders;

    @BeforeEach
    void createFindOrders() {
        findOrders = new FindOrders(cafeRepository, orderDTOAssembler);
    }

    @Test
    void customerId_findOrders_returnsExpectedOrderDTO() {
        List<MenuItem> menuItems = generateMenuItems();
        when(cafeRepository.fetchCafe()).thenReturn(cafe);
        when(cafe.findCustomerById(new CustomerId(CUSTOMER_ID))).thenReturn(customer);
        when(customer.getMenuItemsOrdered()).thenReturn(menuItems);

        findOrders.find(CUSTOMER_ID);

        verify(orderDTOAssembler).toOrderDTO(menuItems);
    }

    private List<MenuItem> generateMenuItems() {
        MenuItem menuItem1 = new MenuItem(ITEM_NAME_1, new Price(PRICE_1), new ArrayList<>());
        MenuItem menuItem2 = new MenuItem(ITEM_NAME_2, new Price(PRICE_2), new ArrayList<>());
        return List.of(menuItem1, menuItem2);
    }
}
