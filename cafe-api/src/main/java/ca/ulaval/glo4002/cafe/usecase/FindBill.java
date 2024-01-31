package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.usecase.assembler.OrderDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.dto.BillDTO;

public class FindBill {

    private final CafeRepository cafeRepository;
    private final OrderDTOAssembler orderDTOAssembler = new OrderDTOAssembler();

    public FindBill(CafeRepository cafeRepository) {
        this.cafeRepository = cafeRepository;
    }

    public BillDTO find(String customerId) {
        Cafe cafe = cafeRepository.fetchCafe();
        Customer customer = cafe.findCustomerById(new CustomerId(customerId));
        Bill bill = customer.tryGetBill();

        return new BillDTO(bill.getSubTotal(), bill.getTaxAmount(), bill.getTipAmount(), bill.getTotal(),
            orderDTOAssembler.toOrderDTO(customer.getMenuItemsOrdered()));
    }
}
