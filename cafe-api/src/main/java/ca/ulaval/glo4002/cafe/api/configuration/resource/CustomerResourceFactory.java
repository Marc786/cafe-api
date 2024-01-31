package ca.ulaval.glo4002.cafe.api.configuration.resource;

import ca.ulaval.glo4002.cafe.api.rest.customer.CustomerAssembler;
import ca.ulaval.glo4002.cafe.api.rest.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.rest.customer.OrdersAssembler;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.usecase.AddCustomer;
import ca.ulaval.glo4002.cafe.usecase.CheckoutCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindBill;
import ca.ulaval.glo4002.cafe.usecase.FindCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindOrders;
import ca.ulaval.glo4002.cafe.usecase.OrderCoffee;
import ca.ulaval.glo4002.cafe.usecase.assembler.CustomerDTOAssembler;
import ca.ulaval.glo4002.cafe.usecase.assembler.OrderDTOAssembler;

public class CustomerResourceFactory {

    private final FindOrders findOrders;
    private final OrderCoffee orderCoffee;
    private final FindCustomer findCustomer;
    private final AddCustomer addCustomer;
    private final CheckoutCustomer checkoutCustomer;
    private final FindBill findBill;

    public CustomerResourceFactory(CafeRepository cafeRepository) {
        CustomerFactory customerFactory = new CustomerFactory();
        CustomerDTOAssembler customerDTOAssembler = new CustomerDTOAssembler();
        OrderDTOAssembler orderDTOAssembler = new OrderDTOAssembler();
        this.findOrders = new FindOrders(cafeRepository, orderDTOAssembler);
        this.orderCoffee = new OrderCoffee(cafeRepository);
        this.findCustomer = new FindCustomer(cafeRepository, customerDTOAssembler);
        this.addCustomer = new AddCustomer(cafeRepository, customerFactory);
        this.checkoutCustomer = new CheckoutCustomer(cafeRepository);
        this.findBill = new FindBill(cafeRepository);
    }

    public CustomerResource create() {
        OrdersAssembler ordersAssembler = new OrdersAssembler();
        CustomerAssembler customerAssembler = new CustomerAssembler();
        return new CustomerResource(addCustomer, findCustomer, customerAssembler,
            ordersAssembler, orderCoffee, findOrders, checkoutCustomer, findBill);
    }
}
