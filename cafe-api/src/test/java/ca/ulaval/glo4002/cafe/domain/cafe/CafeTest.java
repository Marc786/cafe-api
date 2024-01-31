package ca.ulaval.glo4002.cafe.domain.cafe;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CafeTest {

    private static final String CAFE_NAME = "cafe";
    private static final String GROUP_NAME = "mon groupe";
    private static final int GROUP_SIZE = 4;
    private static final String CUSTOMER_ID_STRING = "1234";
    private static final String CUSTOMER_NAME = "sonNom";
    private static final String MENU_ITEM_NAME_1 = "Mocha";
    private static final String MENU_ITEM_NAME_2 = "Latte";
    private static final MenuIngredient MENU_INGREDIENT = new MenuIngredient("spice", 4);
    private static final Price MENU_ITEM_PRICE = new Price(3.50);
    private static final Rate COUNTRY_RATE = new Rate(0);
    private static final Rate STATE_RATE = new Rate(0);
    private static final Rate TIP_RATE = new Rate(0);
    private static final Biller BILLER = new Biller(COUNTRY_RATE, STATE_RATE, TIP_RATE);
    private final CustomerId CUSTOMER_ID = new CustomerId(CUSTOMER_ID_STRING);
    private final CubeList cubeListMock = mock(CubeList.class);
    private final CustomerList customerListMock = mock(CustomerList.class);
    private final ReservationList reservationListMock = mock(ReservationList.class);
    private final Inventory inventoryMock = mock(Inventory.class);
    private final ReservationStrategy reservationStrategyMock = mock(ReservationStrategy.class);
    private final MenuItemList menuItemListMock = mock(MenuItemList.class);

    private List<MenuItem> menuItems;
    private Customer customer;
    private Seat seat;
    private Reservation reservation;
    private Cafe cafe;

    @BeforeEach
    void setup() {
        cafe = new Cafe(CAFE_NAME, cubeListMock, customerListMock, reservationListMock, reservationStrategyMock,
            menuItemListMock, BILLER, inventoryMock);
        customer = createCustomer();
        seat = createSeat();
        reservation = createReservation();
        menuItems = createMenuWithTwoItems();
    }

    @Test
    void aCustomer_addCustomer_customerIsAssignedToFirstFreeSeat() {
        cafe.addCustomer(customer);

        verify(cubeListMock).assignCustomerToFirstFreeSeat(customer.getId());
    }

    @Test
    void aCustomer_addCustomer_customerIsAddedToCustomerList() {
        cafe.addCustomer(customer);

        verify(customerListMock).add(customer);
    }

    @Test
    void aCustomerWithValidGroupName_addCustomerInGroup_customerIsAssignedToReservationFirstFreeSeat() {
        when(reservationListMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(true);

        cafe.addCustomerInGroup(customer, GROUP_NAME);

        verify(cubeListMock).assignCustomerToReservationFirstFreeSeat(customer.getId(), GROUP_NAME);
    }

    @Test
    void aCustomerWithValidGroupName_addCustomerInGroup_customerIsAddedToCustomerList() {
        when(reservationListMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(true);

        cafe.addCustomerInGroup(customer, GROUP_NAME);

        verify(customerListMock).add(customer);
    }

    @Test
    void customerInside_isCustomerAlreadyInside_customerIsInside() {
        when(customerListMock.isCustomerAlreadyInside(customer.getId())).thenReturn(true);

        boolean customerExists = cafe.isCustomerAlreadyInside(customer.getId());

        assertThat(customerExists).isTrue();
    }

    @Test
    void customerNotInside_isCustomerAlreadyInside_customerIsNotInside() {
        when(customerListMock.isCustomerAlreadyInside(customer.getId())).thenReturn(false);

        boolean customerExists = cafe.isCustomerAlreadyInside(customer.getId());

        assertThat(customerExists).isFalse();
    }

    @Test
    void findCustomerSeat_seatIsFound() {
        when(cubeListMock.findCustomerSeat(customer.getId())).thenReturn(seat);

        Seat seat = cafe.findCustomerSeat(customer.getId());

        assertThat(seat).isEqualTo(this.seat);
    }

    @Test
    void findCustomerById_customerIsFound() {
        when(customerListMock.findCustomerById(customer.getId())).thenReturn(customer);

        Customer customer = cafe.findCustomerById(this.customer.getId());

        assertThat(customer).isEqualTo(this.customer);
    }

    @Test
    void existingReservation_doesReservationAlreadyExist_reservationExists() {
        when(reservationListMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(true);

        boolean reservationExists = cafe.doesReservationAlreadyExist(GROUP_NAME);

        assertThat(reservationExists).isTrue();
    }

    @Test
    void nonExistingReservation_doesReservationAlreadyExist_reservationDoesNotExists() {
        when(reservationListMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(false);

        boolean reservationExists = cafe.doesReservationAlreadyExist(GROUP_NAME);

        assertThat(reservationExists).isFalse();
    }

    @Test
    void aReservation_addReservation_reservedWithReservationStrategy() {
        cafe.addReservation(reservation);

        verify(reservationStrategyMock).reserve(any(), eq(reservation));
    }

    @Test
    void aReservation_addReservation_reservationAddedToReservationList() {
        cafe.addReservation(reservation);

        verify(reservationListMock).add(reservation);
    }

    @Test
    void closeCafe_setAllSeatsAvailable() {
        cafe.close();

        verify(cubeListMock).setAllSeatsAvailable();
    }

    @Test
    void closeCafe_removeCustomers() {
        cafe.close();

        verify(customerListMock).clear();
    }

    @Test
    void closeCafe_removeReservations() {
        cafe.close();

        verify(reservationListMock).clear();
    }

    @Test
    void closeCafe_resetInventory() {
        cafe.close();

        verify(inventoryMock).reset();
    }

    @Test
    void addIngredientToInventory_inventoryAddIngredient() {
        List<MenuIngredient> menuIngredients = new ArrayList<>();
        menuIngredients.add(MENU_INGREDIENT);

        cafe.addToInventory(menuIngredients);

        verify(inventoryMock).add(menuIngredients);
    }

    @Test
    void isNotLastCustomerInGroup_checkoutCustomer_unassignsCustomers() {
        when(cubeListMock.findCustomerSeat(CUSTOMER_ID)).thenReturn(seat);
        when(cubeListMock.unassignCustomerIsLastInGroup(CUSTOMER_ID, GROUP_NAME)).thenReturn(false);

        cafe.checkoutCustomer(CUSTOMER_ID);

        verify(cubeListMock).findCustomerSeat(CUSTOMER_ID);
        verify(cubeListMock).unassignCustomerIsLastInGroup(CUSTOMER_ID, seat.getGroupName());
        verify(reservationListMock, never()).remove(GROUP_NAME);
    }

    @Test
    void isLastCustomerInGroup_checkoutCustomer_unassignsCustomers() {
        when(cubeListMock.findCustomerSeat(CUSTOMER_ID)).thenReturn(seat);
        seat.reserve(GROUP_NAME);
        when(cubeListMock.unassignCustomerIsLastInGroup(CUSTOMER_ID, GROUP_NAME)).thenReturn(true);

        cafe.checkoutCustomer(CUSTOMER_ID);

        verify(cubeListMock).findCustomerSeat(CUSTOMER_ID);
        verify(cubeListMock).unassignCustomerIsLastInGroup(CUSTOMER_ID, seat.getGroupName());
    }

    @Test
    void isLastCustomerInGroup_checkoutCustomer_removesReservation() {
        when(cubeListMock.findCustomerSeat(CUSTOMER_ID)).thenReturn(seat);
        seat.reserve(GROUP_NAME);
        when(cubeListMock.unassignCustomerIsLastInGroup(CUSTOMER_ID, GROUP_NAME)).thenReturn(true);

        cafe.checkoutCustomer(CUSTOMER_ID);

        verify(reservationListMock).remove(GROUP_NAME);
    }

    @Test
    void customerInside_checkoutCustomer_createBill() {
        when(cubeListMock.findCustomerSeat(CUSTOMER_ID)).thenReturn(seat);

        cafe.checkoutCustomer(CUSTOMER_ID);

        verify(customerListMock).createBill(eq(CUSTOMER_ID), anyBoolean(), eq(BILLER));
    }

    @Test
    void addOrders_menuItemsAreFound() {
        List<String> orders = List.of(MENU_ITEM_NAME_1, MENU_ITEM_NAME_2);

        cafe.addOrders(customer.getId(), orders);

        verify(menuItemListMock).findMenuItems(orders);
    }

    @Test
    void addOrders_orderAddedToCustomer() {
        List<String> orders = List.of(MENU_ITEM_NAME_1, MENU_ITEM_NAME_2);
        List<MenuItem> ordersMenuItems = createMenuWithTwoItems();
        when(menuItemListMock.findMenuItems(orders)).thenReturn(ordersMenuItems);

        cafe.addOrders(customer.getId(), orders);

        verify(customerListMock).addOrders(customer.getId(), ordersMenuItems);
    }

    @Test
    void addOrders_inventoryIsDecreased() {
        List<String> orders = List.of(MENU_ITEM_NAME_1, MENU_ITEM_NAME_2);
        List<MenuItem> ordersMenuItems = createMenuWithTwoItems();
        List<MenuIngredient> expectedRecipes = List.of(MENU_INGREDIENT, MENU_INGREDIENT);
        when(menuItemListMock.findMenuItems(orders)).thenReturn(ordersMenuItems);

        cafe.addOrders(customer.getId(), orders);

        verify(inventoryMock).decrease(expectedRecipes);
    }

    private List<MenuItem> createMenuWithTwoItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        List<MenuIngredient> recipe1 = new ArrayList<>();
        List<MenuIngredient> recipe2 = new ArrayList<>();
        recipe1.add(MENU_INGREDIENT);
        recipe2.add(MENU_INGREDIENT);
        menuItems.add(new MenuItem(MENU_ITEM_NAME_1, MENU_ITEM_PRICE, recipe1));
        menuItems.add(new MenuItem(MENU_ITEM_NAME_2, MENU_ITEM_PRICE, recipe2));
        return menuItems;
    }

    private Reservation createReservation() {
        return new Reservation(GROUP_NAME, GROUP_SIZE);
    }

    private Seat createSeat() {
        return new Seat(new SeatId(1), Status.OCCUPIED);
    }

    private Customer createCustomer() {
        return new Customer(new CustomerId(CUSTOMER_ID_STRING), CUSTOMER_NAME, menuItems);
    }
}
