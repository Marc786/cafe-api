package ca.ulaval.glo4002.cafe.usecase;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.inventory.InventoryIngredient;
import ca.ulaval.glo4002.cafe.usecase.assembler.IngredientDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FindInventoryTest {

    private final InventoryIngredient AN_INVENTORY_INGREDIENT = new InventoryIngredient("Milk", 2);
    private final IngredientDTO AN_INGREDIENT_DTO = new IngredientDTO("Milk", 2);
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private final IngredientDTOAssembler ingredientDTOAssemblerMock = mock(IngredientDTOAssembler.class);

    private FindInventory findInventory;

    @BeforeEach
    void setup() {
        findInventory = new FindInventory(cafeRepositoryMock);
    }

    @Test
    void find_returnListOfIngredientDTOFromCafeInventory() {
        List<InventoryIngredient> ingredients = List.of(AN_INVENTORY_INGREDIENT);
        Inventory inventory = new Inventory(ingredients);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
        when(cafeMock.getInventory()).thenReturn(inventory);
        List<IngredientDTO> ingredientsDTO = List.of(AN_INGREDIENT_DTO);
        when(ingredientDTOAssemblerMock.fromInventoryToIngredientsDTO(any())).thenReturn(ingredientsDTO);

        List<IngredientDTO> ingredientsDTOReturned = findInventory.find();

        assertThat(ingredientsDTOReturned).isEqualTo(
            ingredientDTOAssemblerMock.fromInventoryToIngredientsDTO(inventory));
    }
}
