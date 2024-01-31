package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class OrderCoffeeTest {

    private static final CustomerId CUSTOMER_ID = new CustomerId("id");
    private static final String ITEM_NAME_1 = "Mocha";
    private static final String ITEM_NAME_2 = "Latte";

    private final CafeRepository cafeRepository = mock(CafeRepository.class);
    private final Cafe cafe = mock(Cafe.class);
    private OrderCoffee orderCoffee = mock(OrderCoffee.class);

    @BeforeEach
    void createOrderCoffee() {
        orderCoffee = new OrderCoffee(cafeRepository);
    }

    @Test
    void order_placeOrder_verifiesAddOrdersIsCalled() {
        List<String> orders = List.of(ITEM_NAME_1, ITEM_NAME_2);
        when(cafeRepository.fetchCafe()).thenReturn(cafe);

        orderCoffee.order(CUSTOMER_ID.getValue(), orders);

        verify(cafe).addOrders(CUSTOMER_ID, orders);
        verify(cafeRepository).save(cafe);
    }
}
