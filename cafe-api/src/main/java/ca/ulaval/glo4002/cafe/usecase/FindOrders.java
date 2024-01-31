package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.usecase.assembler.OrderDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import java.util.List;

public class FindOrders {

    private final CafeRepository cafeRepository;
    private final OrderDTOAssembler orderDTOAssembler;

    public FindOrders(CafeRepository cafeRepository, OrderDTOAssembler orderDTOAssembler) {
        this.cafeRepository = cafeRepository;
        this.orderDTOAssembler = orderDTOAssembler;
    }

    public OrderDTO find(String customerId) {
        Cafe cafe = cafeRepository.fetchCafe();
        Customer customer = cafe.findCustomerById(new CustomerId(customerId));
        List<MenuItem> menuItems = customer.getMenuItemsOrdered();

        return orderDTOAssembler.toOrderDTO(menuItems);
    }
}
