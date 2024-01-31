package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;

public class CloseCafe {

    private final CafeRepository cafeRepository;

    public CloseCafe(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void close() {
        Cafe cafe = cafeRepository.fetchCafe();
        cafe.close();
        cafeRepository.save(cafe);
    }
}
