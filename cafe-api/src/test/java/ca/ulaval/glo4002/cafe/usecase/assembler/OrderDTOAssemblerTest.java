package ca.ulaval.glo4002.cafe.usecase.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class OrderDTOAssemblerTest {

    private static final String ITEM_NAME_1 = "Mocha";
    private static final String ITEM_NAME_2 = "Latte";
    private static final double PRICE_1 = 2.80;
    private static final double PRICE_2 = 3.25;

    @Test
    void menuItems_toOrderDTO_returnExpectedOrderDTO() {
        OrderDTOAssembler orderDTOAssembler = new OrderDTOAssembler();
        List<MenuItem> menuItems = generateMenuItems();
        OrderDTO expectedOrderDTO = new OrderDTO(List.of(ITEM_NAME_1, ITEM_NAME_2));

        OrderDTO orderDTO = orderDTOAssembler.toOrderDTO(menuItems);

        assertThat(orderDTO).isEqualTo(expectedOrderDTO);
    }

    private List<MenuItem> generateMenuItems() {
        MenuItem menuItem1 = new MenuItem(ITEM_NAME_1, new Price(PRICE_1), new ArrayList<>());
        MenuItem menuItem2 = new MenuItem(ITEM_NAME_2, new Price(PRICE_2), new ArrayList<>());
        return List.of(menuItem1, menuItem2);
    }
}
