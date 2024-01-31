package ca.ulaval.glo4002.cafe.domain.cafe;

import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeFactory;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;

import java.util.List;

public class CafeFactory {

    private final CubeFactory cubeFactory = new CubeFactory();

    public Cafe create(CafeConfig cafeConfig) {
        List<Cube> cubes = cubeFactory.createCubes(cafeConfig.getCubeSize(), cafeConfig.getCubeNames());

        return new Cafe(cafeConfig.getOrganizationName(), new CubeList(cubes), new CustomerList(),
            new ReservationList(), cafeConfig.getReservationStrategy(), new MenuItemList(cafeConfig.getMenuItems()),
            new Biller(cafeConfig.getCountryRate(), cafeConfig.getStateRate(), cafeConfig.getTipRate()),
            new Inventory(cafeConfig.getInventoryIngredients()));
    }
}
