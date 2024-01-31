package ca.ulaval.glo4002.cafe.domain.cafe;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CafeFactoryTest {

    private static final int CUBE_SIZE = 2;
    private final List<String> CUBE_NAMES = List.of("Bloom", "Merryweather", "Tinker Bell", "Wanda");
    private final String CAFE_NAME = "toto";
    private final Rate COUNTRY_RATE = new Rate(0);
    private final Rate STATE_RATE = new Rate(0);
    private final Rate TIP_RATE = new Rate(0);
    private final Biller BILLER = new Biller(COUNTRY_RATE, STATE_RATE, TIP_RATE);
    private final ReservationStrategy reservationStrategy = new DefaultStrategy();

    @Test
    void createCafe_returnsExpectedCafe() {
        CafeFactory cafeFactory = new CafeFactory();
        CafeConfig cafeConfig = new CafeConfig(new DefaultStrategy(), CAFE_NAME, CUBE_SIZE, COUNTRY_RATE, STATE_RATE,
            TIP_RATE);
        Cafe expectedCafe = generateACafe(cafeConfig);

        Cafe cafe = cafeFactory.create(cafeConfig);

        assertThat(cafe).isEqualTo(expectedCafe);
    }

    private Cafe generateACafe(CafeConfig cafeConfig) {
        return new Cafe(CAFE_NAME, generateCubes(), new CustomerList(), new ReservationList(), reservationStrategy,
            new MenuItemList(cafeConfig.getMenuItems()), BILLER, new Inventory(cafeConfig.getInventoryIngredients()));
    }

    private CubeList generateCubes() {
        List<Cube> cubes = new ArrayList<>();
        cubes.add(new Cube(CUBE_NAMES.get(0), generateSeats(1)));
        cubes.add(new Cube(CUBE_NAMES.get(1), generateSeats(3)));
        cubes.add(new Cube(CUBE_NAMES.get(2), generateSeats(5)));
        cubes.add(new Cube(CUBE_NAMES.get(3), generateSeats(7)));

        return new CubeList(cubes);
    }

    private List<Seat> generateSeats(int firstSeatId) {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat(new SeatId(firstSeatId), Status.AVAILABLE));
        seats.add(new Seat(new SeatId(firstSeatId + 1), Status.AVAILABLE));

        return seats;
    }
}
