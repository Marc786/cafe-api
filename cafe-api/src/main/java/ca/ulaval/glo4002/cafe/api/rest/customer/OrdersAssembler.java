package ca.ulaval.glo4002.cafe.api.rest.customer;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.OrdersResponse;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

public class OrdersAssembler {

    public OrdersResponse toOrderResponse(OrderDTO orderDTO) {
        return new OrdersResponse(orderDTO.getOrders());
    }
}
