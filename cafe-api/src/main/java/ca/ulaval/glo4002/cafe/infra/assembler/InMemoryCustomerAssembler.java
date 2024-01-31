package ca.ulaval.glo4002.cafe.infra.assembler;

import static java.util.Objects.isNull;

import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryBill;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryCustomerAssembler {

    private final InMemoryMenuItemsAssembler inMemoryMenuItemsAssembler = new InMemoryMenuItemsAssembler();

    public List<InMemoryCustomer> fromCustomersToInMemoryCustomers(List<Customer> customers) {
        return customers.stream().map(this::createInMemoryCustomer)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Customer> fromInMemoryCustomersToCustomers(List<InMemoryCustomer> inMemoryCustomers) {
        return inMemoryCustomers.stream().map(this::createCustomer).collect(Collectors.toCollection(ArrayList::new));
    }

    private InMemoryCustomer createInMemoryCustomer(Customer customer) {
        return new InMemoryCustomer(customer.getId().getValue(), customer.getName(),
            inMemoryMenuItemsAssembler.fromMenuItemsToInMemoryMenuItems(customer.getMenuItemsOrdered()),
            fromBillToInMemoryBill(customer.getBill()));
    }

    private Customer createCustomer(InMemoryCustomer inMemoryCustomer) {
        return new Customer(new CustomerId(inMemoryCustomer.getId()), inMemoryCustomer.getName(),
            inMemoryMenuItemsAssembler.fromInMemoryMenusItemToMenuItems(inMemoryCustomer.getMenuItemsOrdered()),
            fromInMemoryBillToBill(inMemoryCustomer.getInMemoryBill()));
    }

    private InMemoryBill fromBillToInMemoryBill(Bill bill) {
        if (!isNull(bill)) {
            return new InMemoryBill(bill.getSubTotal().getDoubleValue(), bill.getTaxAmount().getDoubleValue(),
                bill.getTipAmount().getDoubleValue(), bill.getTotal().getDoubleValue());
        } else
            return null;
    }

    private Bill fromInMemoryBillToBill(InMemoryBill inMemoryBill) {
        if (!isNull(inMemoryBill)) {
            Price subTotal = new Price(inMemoryBill.getSubTotal());
            Price taxTotal = new Price(inMemoryBill.getTaxAmount());
            Price tipPrice = new Price(inMemoryBill.getTipAmount());
            Price total = new Price(inMemoryBill.getTotal());

            return new Bill(subTotal, taxTotal, tipPrice, total);
        } else
            return null;
    }
}
