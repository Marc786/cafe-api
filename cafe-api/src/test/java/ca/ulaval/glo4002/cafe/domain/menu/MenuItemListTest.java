package ca.ulaval.glo4002.cafe.domain.menu;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InvalidMenuOrderException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class MenuItemListTest {

    private static final String NAME_1 = "Mocha";
    private static final String NAME_2 = "Espresso";
    private static final String NAME_3 = "Latte";
    private static final String INVALID_NAME_1 = "beigne";
    private static final String INVALID_NAME_2 = "burger";
    private static final MenuItem MENU_ITEM_1 = new MenuItem(NAME_1, new Price(2.35), new ArrayList<>());
    private static final MenuItem MENU_ITEM_2 = new MenuItem(NAME_2, new Price(1.55), new ArrayList<>());
    private static final MenuItem MENU_ITEM_3 = new MenuItem(NAME_3, new Price(3.95), new ArrayList<>());
    private List<MenuItem> menuItems;
    private MenuItemList menuItemList;

    @BeforeEach
    void createMenuItemList() {
        menuItems = List.of(MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3);
        menuItemList = new MenuItemList(menuItems);
    }

    @Test
    void menuItemNames_findMenuItems_returnsExpected() {
        List<String> menuItemNames = List.of(NAME_1, NAME_2);
        List<MenuItem> expectedMenuItems = List.of(MENU_ITEM_1, MENU_ITEM_2);

        List<MenuItem> menuItems = menuItemList.findMenuItems(menuItemNames);

        assertThat(menuItems).isEqualTo(expectedMenuItems);
    }

    @Test
    void invalidMenuItemNames_findMenuItems_throwsInvalidMenuOrderException() {
        List<String> menuItemNames = List.of(INVALID_NAME_1, INVALID_NAME_2);

        assertThrows(InvalidMenuOrderException.class, () -> menuItemList.findMenuItems(menuItemNames));
    }
}
