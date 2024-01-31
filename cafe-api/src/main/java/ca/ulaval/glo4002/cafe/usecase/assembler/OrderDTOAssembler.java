package ca.ulaval.glo4002.cafe.usecase.assembler;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import java.util.List;

public class OrderDTOAssembler {

    public OrderDTO toOrderDTO(List<MenuItem> menuItems) {
        List<String> menuItemNames = menuItems.stream().map(MenuItem::getName).toList();
        return new OrderDTO(menuItemNames);
    }
}
