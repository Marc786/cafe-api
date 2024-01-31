package ca.ulaval.glo4002.cafe.infra.assembler;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.reservation.Reservation;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.infra.dto.*;

import java.util.List;

public class InMemoryCafeAssembler {

    private final InMemoryCubeAssembler inMemoryCubeAssembler = new InMemoryCubeAssembler();
    private final InMemoryCustomerAssembler inMemoryCustomerAssembler = new InMemoryCustomerAssembler();
    private final InMemoryReservationAssembler inMemoryReservationAssembler = new InMemoryReservationAssembler();
    private final InMemoryMenuItemsAssembler inMemoryMenuItemsAssembler = new InMemoryMenuItemsAssembler();
    private final InMemoryIngredientAssembler inMemoryIngredientAssembler = new InMemoryIngredientAssembler();
    private final InMemoryStrategyAssembler inMemoryStrategyAssembler;

    public InMemoryCafeAssembler(ReservationStrategyFactory reservationStrategyFactory) {
        inMemoryStrategyAssembler = new InMemoryStrategyAssembler(reservationStrategyFactory);
    }

    public InMemoryCafe fromCafeToInMemoryCafe(Cafe cafe) {
        List<InMemoryCube> inMemoryCubes = inMemoryCubeAssembler.fromCubesToInMemoryCubes(cafe.getCubes());
        List<InMemoryCustomer> inMemoryCustomers = inMemoryCustomerAssembler.fromCustomersToInMemoryCustomers(
            cafe.getCustomersList());
        List<InMemoryReservation> inMemoryReservations = inMemoryReservationAssembler.fromReservationsToInMemoryReservations(
            cafe.getReservations());
        InMemoryStrategy inMemoryStrategy = inMemoryStrategyAssembler.fromReservationStrategyToInMemoryStrategy(
            cafe.getReservationStrategy());
        List<InMemoryMenuItem> inMemoryMenuItems = inMemoryMenuItemsAssembler.fromMenuItemsToInMemoryMenuItems(
            cafe.getMenuItems());
        InMemoryRate inMemoryCountryRate = new InMemoryRate(cafe.getBiller().getCountryRate().getDoubleValue());
        InMemoryRate inMemoryStateRate = new InMemoryRate(cafe.getBiller().getStateRate().getDoubleValue());
        InMemoryRate inMemoryTipRate = new InMemoryRate(cafe.getBiller().getTipRate().getDoubleValue());
        List<InMemoryInventoryIngredient> inMemoryInventory = inMemoryIngredientAssembler.fromInventoryIngredientsToInMemoryIngredients(
            cafe.getInventory().getIngredients());

        return new InMemoryCafe(cafe.getName(), inMemoryCubes, inMemoryCustomers, inMemoryReservations,
            inMemoryStrategy, inMemoryMenuItems, inMemoryCountryRate, inMemoryStateRate, inMemoryInventory,
            inMemoryTipRate);
    }

    public Cafe fromInMemoryCafeToCafe(InMemoryCafe inMemoryCafe) {
        List<Cube> cubes = inMemoryCubeAssembler.fromInMemoryCubesToCubes(inMemoryCafe.getInMemoryCubes());
        List<Customer> customers = inMemoryCustomerAssembler.fromInMemoryCustomersToCustomers(
            inMemoryCafe.getInMemoryCustomers());
        List<Reservation> reservations = inMemoryReservationAssembler.fromInMemoryReservationsToReservations(
            inMemoryCafe.getInMemoryReservations());
        ReservationStrategy reservationStrategy = inMemoryStrategyAssembler.fromInMemoryStrategyToReservationStrategy(
            inMemoryCafe.getInMemoryStrategy());
        List<MenuItem> menuItems = inMemoryMenuItemsAssembler.fromInMemoryMenusItemToMenuItems(
            inMemoryCafe.getInMemoryMenuItems());
        Rate countryRate = new Rate(inMemoryCafe.getInMemoryCountryRate().getRate());
        Rate stateRate = new Rate(inMemoryCafe.getInMemoryStateRate().getRate());
        Rate tipRate = new Rate(inMemoryCafe.getInMemoryTipRate().getRate());
        List<InventoryIngredient> inventory = inMemoryIngredientAssembler.fromInMemoryIngredientsToInventoryIngredients(
            inMemoryCafe.getInMemoryInventory());

        return new Cafe(inMemoryCafe.getName(), new CubeList(cubes), new CustomerList(customers),
            new ReservationList(reservations), reservationStrategy, new MenuItemList(menuItems),
            new Biller(countryRate, stateRate, tipRate), new Inventory(inventory));
    }
}
