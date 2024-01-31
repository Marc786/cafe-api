package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import java.util.List;

public class OrderCoffee {

    private final CafeRepository cafeRepository;

    public OrderCoffee(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public void order(String customerId, List<String> orders) {
        Cafe cafe = cafeRepository.fetchCafe();

        cafe.addOrders(new CustomerId(customerId), orders);

        cafeRepository.save(cafe);
    }
}
