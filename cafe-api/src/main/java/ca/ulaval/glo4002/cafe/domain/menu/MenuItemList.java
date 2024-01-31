package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.customer.exception.InvalidMenuOrderException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MenuItemList {

    private final List<MenuItem> menuItems;

    public MenuItemList(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public List<MenuItem> findMenuItems(List<String> menuItemNames) {
        return menuItemNames.stream().map(this::findMenuItem).collect(Collectors.toList());
    }

    private MenuItem findMenuItem(String name) {
        return menuItems.stream()
            .filter(menuItem -> menuItem.hasName(name))
            .findFirst()
            .orElseThrow(InvalidMenuOrderException::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MenuItemList that = (MenuItemList) o;
        return Objects.equals(menuItems, that.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItems);
    }
}
