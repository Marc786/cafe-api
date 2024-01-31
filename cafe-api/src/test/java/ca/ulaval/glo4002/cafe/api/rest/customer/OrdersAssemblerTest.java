package ca.ulaval.glo4002.cafe.api.rest.customer;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.OrdersResponse;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import org.junit.jupiter.api.Test;

import java.util.List;

class OrdersAssemblerTest {

    private static final List<String> ORDERS = List.of("Mocha", "Latte");

    @Test
    void orderDTO_toOrderResponse_returnsExpectedOrderResponse() {
        OrdersAssembler ordersAssembler = new OrdersAssembler();
        OrdersResponse expectedOrdersResponse = new OrdersResponse(ORDERS);

        OrdersResponse ordersResponse = ordersAssembler.toOrderResponse(new OrderDTO(ORDERS));

        assertThat(ordersResponse).isEqualTo(expectedOrdersResponse);
    }
}
