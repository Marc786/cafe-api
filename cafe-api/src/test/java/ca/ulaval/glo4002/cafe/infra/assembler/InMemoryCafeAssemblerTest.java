package ca.ulaval.glo4002.cafe.infra.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.FullCubeStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.NoLonersStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.infra.dto.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class InMemoryCafeAssemblerTest {

    private final static String CAFE_NAME = "Les 4-Fées";
    private final static String CUBE_NAME = "un cube";
    private static final CustomerId CUSTOMER_ID_1 = new CustomerId("unid");
    private static final CustomerId CUSTOMER_ID_2 = new CustomerId("deuxid");
    private static final CustomerId CUSTOMER_ID_3 = new CustomerId("troisid");
    private static final String CUSTOMER_NAME = "unnom";
    private static final String GROUP_NAME = "unGroupe";
    private static final int GROUP_SIZE = 3;
    private static final String NAME_1 = "Mocha";
    private static final String NAME_2 = "Espresso";
    private static final double PRICE_1 = 1.5;
    private static final double PRICE_2 = 2.5;
    private static final Rate COUNTRY_RATE = new Rate(0);
    private static final Rate STATE_RATE = new Rate(0);
    private static final Rate TIP_RATE = new Rate(0);
    private static final Biller BILLER = new Biller(COUNTRY_RATE, STATE_RATE, TIP_RATE);
    private static final String INGREDIENT_NAME_1 = "lait";
    private static final int INGREDIENT_QUANTITY_1 = 4;
    private static final String INGREDIENT_NAME_2 = "eau";
    private static final int INGREDIENT_QUANTITY_2 = 8;
    private static final double SUB_TOTAL = 12;
    private static final double TAX_AMOUNT = 2;
    private static final double TIP_AMOUNT = 1;
    private static final double TOTAL = 15;
    private static final String CHOCOLATE = "Chocolate";
    private static final String ESPRESSO = "Espresso";
    private static final String MILK = "Milk";
    private static final String WATER = "Water";

    private InMemoryCafeAssembler inMemoryCafeAssembler;

    @BeforeEach
    void createInMemoryCafeAssembler() {
        inMemoryCafeAssembler = new InMemoryCafeAssembler(new ReservationStrategyFactory());
    }

    @Test
    void cafeWithDefaultStrategy_convertToInMemoryCafe_returnExpectedInMemoryCafe() {
        Cafe cafe = generateACafe(new DefaultStrategy());
        InMemoryCafe expectedInMemoryCafe = generateInMemoryCafe(ReservationType.DEFAULT);

        InMemoryCafe inMemoryCafe = inMemoryCafeAssembler.fromCafeToInMemoryCafe(cafe);

        assertThat(inMemoryCafe).isEqualTo(expectedInMemoryCafe);
    }

    @Test
    void cafeWithNoLonersStrategy_convertToInMemoryCafe_returnExpectedInMemoryCafe() {
        Cafe cafe = generateACafe(new NoLonersStrategy());
        InMemoryCafe expectedInMemoryCafe = generateInMemoryCafe(ReservationType.NO_LONERS);

        InMemoryCafe inMemoryCafe = inMemoryCafeAssembler.fromCafeToInMemoryCafe(cafe);

        assertThat(inMemoryCafe).isEqualTo(expectedInMemoryCafe);
    }

    @Test
    void cafeWithFullCubesStrategy_convertToInMemoryCafe_returnExpectedInMemoryCafe() {
        Cafe cafe = generateACafe(new FullCubeStrategy());
        InMemoryCafe expectedInMemoryCafe = generateInMemoryCafe(ReservationType.FULL_CUBES);

        InMemoryCafe inMemoryCafe = inMemoryCafeAssembler.fromCafeToInMemoryCafe(cafe);

        assertThat(inMemoryCafe).isEqualTo(expectedInMemoryCafe);
    }

    @Test
    void inMemoryCafeWithDefaultStrategy_convertToCafe_returnExpectedCafe() {
        InMemoryCafe inMemoryCafe = generateInMemoryCafe(ReservationType.DEFAULT);
        Cafe expectedCafe = generateACafe(new DefaultStrategy());

        Cafe cafe = inMemoryCafeAssembler.fromInMemoryCafeToCafe(inMemoryCafe);

        assertThat(cafe).isEqualTo(expectedCafe);
    }

    private Cafe generateACafe(ReservationStrategy strategy) {
        return new Cafe(CAFE_NAME, generateCubes(), generateCustomers(), generateReservations(), strategy,
            new MenuItemList(generateMenuItems()), BILLER, new Inventory(generateInventoryIngredients()));
    }

    private InMemoryCafe generateInMemoryCafe(ReservationType reservationType) {
        return new InMemoryCafe(CAFE_NAME, generateInMemoryCubes(), generateInMemoryCustomers(),
            generateInMemoryReservations(), new InMemoryStrategy(reservationType), generateInMemoryMenuItems(),
            new InMemoryRate(COUNTRY_RATE.getDoubleValue()), new InMemoryRate(STATE_RATE.getDoubleValue()),
            generateInMemoryInventoryIngredients(), new InMemoryRate(TIP_RATE.getDoubleValue()));
    }

    private List<InventoryIngredient> generateInventoryIngredients() {
        List<InventoryIngredient> inventory = new ArrayList<>();
        inventory.add(new InventoryIngredient(CHOCOLATE, 0));
        inventory.add(new InventoryIngredient(ESPRESSO, 0));
        inventory.add(new InventoryIngredient(MILK, 0));
        inventory.add(new InventoryIngredient(WATER, 0));
        return inventory;
    }

    private List<InMemoryInventoryIngredient> generateInMemoryInventoryIngredients() {
        List<InMemoryInventoryIngredient> inventory = new ArrayList<>();
        inventory.add(new InMemoryInventoryIngredient(CHOCOLATE, 0));
        inventory.add(new InMemoryInventoryIngredient(ESPRESSO, 0));
        inventory.add(new InMemoryInventoryIngredient(MILK, 0));
        inventory.add(new InMemoryInventoryIngredient(WATER, 0));
        return inventory;
    }

    private CustomerList generateCustomers() {
        CustomerList customers = new CustomerList();
        customers.add(new Customer(CUSTOMER_ID_1, CUSTOMER_NAME, generateMenuItemsOrdered(), generateBill()));
        return customers;
    }

    private List<InMemoryCustomer> generateInMemoryCustomers() {
        List<InMemoryCustomer> customers = new ArrayList<>();
        customers.add(
            new InMemoryCustomer(CUSTOMER_ID_1.getValue(), CUSTOMER_NAME, generateInMemoryMenuItemsOrdered(),
                generateInMemoryBill()));
        return customers;
    }

    private Bill generateBill() {
        return new Bill(new Price(SUB_TOTAL), new Price(TAX_AMOUNT), new Price(TIP_AMOUNT), new Price(TOTAL));
    }

    private InMemoryBill generateInMemoryBill() {
        return new InMemoryBill(SUB_TOTAL, TAX_AMOUNT, TIP_AMOUNT, TOTAL);
    }

    private ReservationList generateReservations() {
        ReservationList reservations = new ReservationList();
        reservations.add(new Reservation(GROUP_NAME, GROUP_SIZE));
        return reservations;
    }

    private List<InMemoryReservation> generateInMemoryReservations() {
        return List.of(new InMemoryReservation(GROUP_NAME, GROUP_SIZE));
    }

    private CubeList generateCubes() {
        return new CubeList(List.of(new Cube(CUBE_NAME, generateSeats())));
    }

    private List<InMemoryCube> generateInMemoryCubes() {
        return List.of(new InMemoryCube(CUBE_NAME, generateInMemorySeats()));
    }

    private List<MenuItem> generateMenuItems() {
        MenuItem menuItem1 = new MenuItem(NAME_1, new Price(PRICE_1),
            generateMenuIngredients(INGREDIENT_NAME_1, INGREDIENT_QUANTITY_1));
        MenuItem menuItem2 = new MenuItem(NAME_2, new Price(PRICE_2),
            generateMenuIngredients(INGREDIENT_NAME_2, INGREDIENT_QUANTITY_2));
        return List.of(menuItem1, menuItem2);
    }

    private List<InMemoryMenuItem> generateInMemoryMenuItems() {
        InMemoryMenuItem inMemoryMenuItem1 = new InMemoryMenuItem(NAME_1, PRICE_1,
            generateInMemoryIngredients(INGREDIENT_NAME_1, INGREDIENT_QUANTITY_1));
        InMemoryMenuItem inMemoryMenuItem2 = new InMemoryMenuItem(NAME_2, PRICE_2,
            generateInMemoryIngredients(INGREDIENT_NAME_2, INGREDIENT_QUANTITY_2));
        return List.of(inMemoryMenuItem1, inMemoryMenuItem2);
    }

    private List<MenuItem> generateMenuItemsOrdered() {
        return List.of(
            new MenuItem(NAME_1, new Price(PRICE_1),
                generateMenuIngredients(INGREDIENT_NAME_1, INGREDIENT_QUANTITY_1)));
    }

    private List<InMemoryMenuItem> generateInMemoryMenuItemsOrdered() {
        return List.of(new InMemoryMenuItem(NAME_1, PRICE_1,
            generateInMemoryIngredients(INGREDIENT_NAME_1, INGREDIENT_QUANTITY_1)));
    }

    private List<MenuIngredient> generateMenuIngredients(String ingredientName, int ingredientQuantity) {
        return List.of(new MenuIngredient(ingredientName, ingredientQuantity));
    }

    private List<InMemoryInventoryIngredient> generateInMemoryIngredients(String ingredientName,
        int ingredientQuantity) {
        return List.of(new InMemoryInventoryIngredient(ingredientName, ingredientQuantity));
    }

    private List<Seat> generateSeats() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(new SeatId(1), Status.AVAILABLE, CUSTOMER_ID_1, GROUP_NAME));
        seats.add(new Seat(new SeatId(2), Status.RESERVED, CUSTOMER_ID_2, GROUP_NAME));
        seats.add(new Seat(new SeatId(3), Status.OCCUPIED, CUSTOMER_ID_3, GROUP_NAME));

        return seats;
    }

    private List<InMemorySeat> generateInMemorySeats() {
        List<InMemorySeat> seats = new ArrayList<>();
        seats.add(new InMemorySeat(1, Status.AVAILABLE, CUSTOMER_ID_1, GROUP_NAME));
        seats.add(new InMemorySeat(2, Status.RESERVED, CUSTOMER_ID_2, GROUP_NAME));
        seats.add(new InMemorySeat(3, Status.OCCUPIED, CUSTOMER_ID_3, GROUP_NAME));

        return seats;
    }
}
