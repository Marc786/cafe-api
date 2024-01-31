package ca.ulaval.glo4002.cafe.usecase.dto;

import java.util.List;
import java.util.Objects;

public class OrderDTO {

    private final List<String> orders;

    public OrderDTO(List<String> orders) {
        this.orders = orders;
    }

    public List<String> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(orders, orderDTO.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }
}
