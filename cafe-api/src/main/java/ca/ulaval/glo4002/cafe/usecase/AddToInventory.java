package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;

import java.util.List;

public class AddToInventory {


    private final CafeRepository cafeRepository;

    public AddToInventory(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void add(List<MenuIngredient> menuIngredients) {
        Cafe cafe = cafeRepository.fetchCafe();

        cafe.addToInventory(menuIngredients);

        cafeRepository.save(cafe);
    }
}
