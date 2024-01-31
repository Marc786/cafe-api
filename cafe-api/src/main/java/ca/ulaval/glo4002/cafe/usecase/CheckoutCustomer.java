package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

public class CheckoutCustomer {

    private final CafeRepository cafeRepository;

    public CheckoutCustomer(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void checkout(CustomerId id) {
        Cafe cafe = cafeRepository.fetchCafe();
        cafe.checkoutCustomer(id);
        cafeRepository.save(cafe);
    }
}
