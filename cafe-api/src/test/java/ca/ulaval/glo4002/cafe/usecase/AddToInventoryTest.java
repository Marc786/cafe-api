package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class AddToInventoryTest {

    private final MenuIngredient menuIngredient = new MenuIngredient("Milk", 2);
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final Cafe cafeMock = mock(Cafe.class);

    private AddToInventory addToInventory;


    @BeforeEach
    void setup() {
        addToInventory = new AddToInventory(cafeRepositoryMock);
    }

    @Test
    void add_ingredientsAreAddedToCafe() {
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
        List<MenuIngredient> menuIngredients = List.of(menuIngredient);

        addToInventory.add(menuIngredients);

        verify(cafeMock).addToInventory(menuIngredients);
    }

    @Test
    void add_cafeRepositoryIsSaved() {
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
        List<MenuIngredient> menuIngredients = List.of(menuIngredient);

        addToInventory.add(menuIngredients);

        verify(cafeRepositoryMock).save(cafeMock);
    }
}
