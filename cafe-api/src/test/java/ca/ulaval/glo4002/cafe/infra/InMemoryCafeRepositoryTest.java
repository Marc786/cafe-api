package ca.ulaval.glo4002.cafe.infra;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cube.CubeList;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerList;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.domain.menu.MenuItemList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationList;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.Biller;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.infra.assembler.InMemoryCafeAssembler;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryCafe;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryRate;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryStrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class InMemoryCafeRepositoryTest {

    private static final String CAFE_NAME = "Coffee";
    private final static Rate DEFAULT_RATE = new Rate(0);
    private static final Biller BILLER = new Biller(DEFAULT_RATE, DEFAULT_RATE, DEFAULT_RATE);
    private static final String CHOCOLATE = "Chocolate";
    private static final String ESPRESSO = "Espresso";
    private static final String MILK = "Milk";
    private static final String WATER = "Water";
    private final ReservationStrategy reservationStrategy = new DefaultStrategy();
    private final InMemoryCafeAssembler inMemoryCafeAssemblerMock = mock(InMemoryCafeAssembler.class);
    private InMemoryCafeRepository inMemoryCafeRepository;

    @BeforeEach
    void setup() {
        inMemoryCafeRepository = new InMemoryCafeRepository(inMemoryCafeAssemblerMock);
    }

    @Test
    void created_returnsNullMemoryCafe() {
        assertNull(inMemoryCafeRepository.fetchCafe());
    }

    @Test
    void created_fromInMemoryCafeToCafeIsCalled() {
        inMemoryCafeRepository.fetchCafe();

        verify(inMemoryCafeAssemblerMock).fromInMemoryCafeToCafe(any());
    }

    @Test
    void saveCafe_isUpdatedWithNewOne() {
        Cafe expectedCafe = createCafe();
        InMemoryCafe inMemoryCafe = generateInMemoryCafe();
        when(inMemoryCafeAssemblerMock.fromCafeToInMemoryCafe(expectedCafe)).thenReturn(inMemoryCafe);
        when(inMemoryCafeAssemblerMock.fromInMemoryCafeToCafe(any())).thenReturn(expectedCafe);

        inMemoryCafeRepository.save(expectedCafe);

        assertThat(inMemoryCafeRepository.fetchCafe()).isEqualTo(expectedCafe);
    }

    @Test
    void saveCafe_fromCafeToInMemoryCafeIsCalled() {
        Cafe expectedCafe = createCafe();

        inMemoryCafeRepository.save(expectedCafe);

        verify(inMemoryCafeAssemblerMock).fromCafeToInMemoryCafe(expectedCafe);
    }

    @Test
    void cafeAndCafeAssembler_createCafeRepository_cafeToInMemoryCafeIsCalled() {
        Cafe cafe = createCafe();

        inMemoryCafeRepository = new InMemoryCafeRepository(cafe, inMemoryCafeAssemblerMock);

        verify(inMemoryCafeAssemblerMock).fromCafeToInMemoryCafe(cafe);
    }

    private InMemoryCafe generateInMemoryCafe() {
        return new InMemoryCafe(CAFE_NAME, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
            new InMemoryStrategy(ReservationType.DEFAULT), Collections.emptyList(),
            new InMemoryRate(DEFAULT_RATE.getDoubleValue()), new InMemoryRate(DEFAULT_RATE.getDoubleValue()),
            new ArrayList<>(), new InMemoryRate(DEFAULT_RATE.getDoubleValue()));
    }

    private Cafe createCafe() {
        return new Cafe(CAFE_NAME, new CubeList(Collections.emptyList()), new CustomerList(), new ReservationList(),
            reservationStrategy, new MenuItemList(Collections.emptyList()), BILLER,
            new Inventory(generateInventoryIngredients()));
    }

    private List<InventoryIngredient> generateInventoryIngredients() {
        List<InventoryIngredient> inventory = new ArrayList<>();
        inventory.add(new InventoryIngredient(CHOCOLATE, 0));
        inventory.add(new InventoryIngredient(ESPRESSO, 0));
        inventory.add(new InventoryIngredient(MILK, 0));
        inventory.add(new InventoryIngredient(WATER, 0));
        return inventory;
    }
}
