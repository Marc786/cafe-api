package ca.ulaval.glo4002.cafe.usecase.assembler;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.SeatDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CafeDTOAssemblerTest {

    private static final String CAFE_NAME = "Les 4-Fées";
    private static final String CUBE_NAME_1 = "gogo";
    private static final String CUBE_NAME_2 = "jaco";
    private static final String NAME_1 = "pogo";
    private static final String NAME_2 = "kraft dinner";
    private static final double PRICE_1 = 35.6;
    private static final double PRICE_2 = 78.01;
    private static final String INGREDIENT_NAME_1 = "lait";
    private static final String INGREDIENT_NAME_2 = "soda";
    private static final int INGREDIENT_QUANTITY_1 = 34;
    private static final int INGREDIENT_QUANTITY_2 = 9;
    private static final CustomerId CUSTOMER_ID_1 = new CustomerId("123abc");
    private static final CustomerId CUSTOMER_ID_2 = new CustomerId("456def");
    private static final CustomerId CUSTOMER_ID_3 = new CustomerId("007");
    private static final String GROUP_NAME = "les cool";
    private final Rate DEFAULT_RATE = new Rate(0);
    private final Biller BILLER = new Biller(DEFAULT_RATE, DEFAULT_RATE, DEFAULT_RATE);
    private final ReservationStrategy reservationStrategy = new DefaultStrategy();

    @Test
    void convertCafeToCafe_returnsExpectedCafe() {
        CafeDTOAssembler cafeAssembler = new CafeDTOAssembler();
        Cafe cafe = createCafe();
        List<CubeDTO> expectedCubes = createCubesDTO();

        CafeDTO cafeDTO = cafeAssembler.cafeToCafeDTO(cafe);

        assertThat(cafeDTO.name()).isEqualTo(CAFE_NAME);
        assertThat(cafeDTO.cubes()).isEqualTo(expectedCubes);
    }

    private Cafe createCafe() {
        List<Cube> cubes = createCubes();
        return new Cafe(CAFE_NAME, new CubeList(cubes), new CustomerList(), new ReservationList(),
            reservationStrategy, new MenuItemList(generateMenuItems()), BILLER,
            new Inventory(generateInventoryIngredients()));
    }

    private List<MenuItem> generateMenuItems() {
        MenuItem menuItem1 = new MenuItem(NAME_1, new Price(PRICE_1),
            generateMenuIngredients(INGREDIENT_NAME_1, INGREDIENT_QUANTITY_1));
        MenuItem menuItem2 = new MenuItem(NAME_2, new Price(PRICE_2),
            generateMenuIngredients(INGREDIENT_NAME_2, INGREDIENT_QUANTITY_2));
        return List.of(menuItem1, menuItem2);
    }

    private List<MenuIngredient> generateMenuIngredients(String ingredientName, int ingredientQuantity) {
        return List.of(new MenuIngredient(ingredientName, ingredientQuantity));
    }

    private List<InventoryIngredient> generateInventoryIngredients() {
        List<InventoryIngredient> inventory = new ArrayList<>();
        inventory.add(new InventoryIngredient("Chocolate", 0));
        inventory.add(new InventoryIngredient("Espresso", 0));
        inventory.add(new InventoryIngredient("Milk", 0));
        inventory.add(new InventoryIngredient("Water", 0));
        return inventory;
    }

    private List<Cube> createCubes() {
        Cube cube1 = new Cube(CUBE_NAME_1, generateSeats(1));
        Cube cube2 = new Cube(CUBE_NAME_2, generateSeats(4));

        return List.of(cube1, cube2);
    }

    private List<CubeDTO> createCubesDTO() {
        CubeDTO cube1 = new CubeDTO(CUBE_NAME_1, generateInMemorySeats(1));
        CubeDTO cube2 = new CubeDTO(CUBE_NAME_2, generateInMemorySeats(4));

        return List.of(cube1, cube2);
    }

    private List<Seat> generateSeats(int firstSeatNumber) {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(new SeatId(firstSeatNumber), Status.AVAILABLE, CUSTOMER_ID_1, GROUP_NAME));
        seats.add(new Seat(new SeatId(firstSeatNumber + 1), Status.RESERVED, CUSTOMER_ID_2, GROUP_NAME));
        seats.add(new Seat(new SeatId(firstSeatNumber + 2), Status.OCCUPIED, CUSTOMER_ID_3, GROUP_NAME));

        return seats;
    }

    private List<SeatDTO> generateInMemorySeats(int firstSeatNumber) {
        List<SeatDTO> seats = new ArrayList<>();
        seats.add(new SeatDTO(new SeatId(firstSeatNumber), Status.AVAILABLE, CUSTOMER_ID_1, GROUP_NAME));
        seats.add(new SeatDTO(new SeatId(firstSeatNumber + 1), Status.RESERVED, CUSTOMER_ID_2, GROUP_NAME));
        seats.add(new SeatDTO(new SeatId(firstSeatNumber + 2), Status.OCCUPIED, CUSTOMER_ID_3, GROUP_NAME));

        return seats;
    }
}
