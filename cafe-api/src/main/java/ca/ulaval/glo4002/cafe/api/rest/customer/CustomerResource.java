package ca.ulaval.glo4002.cafe.api.rest.customer;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.BillResponse;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CheckoutRequest;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.OrdersRequest;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.usecase.AddCustomer;
import ca.ulaval.glo4002.cafe.usecase.CheckoutCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindBill;
import ca.ulaval.glo4002.cafe.usecase.FindCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindOrders;
import ca.ulaval.glo4002.cafe.usecase.OrderCoffee;
import ca.ulaval.glo4002.cafe.usecase.dto.BillDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final AddCustomer addCustomer;
    private final FindCustomer findCustomer;
    private final CustomerAssembler customerAssembler;
    private final OrdersAssembler ordersAssembler;
    private final OrderCoffee orderCoffee;
    private final FindOrders findOrders;
    private final CheckoutCustomer checkoutCustomer;
    private final FindBill findBill;

    public CustomerResource(AddCustomer addCustomer, FindCustomer findCustomer, CustomerAssembler customerAssembler,
        OrdersAssembler ordersAssembler, OrderCoffee orderCoffee, FindOrders findOrders,
        CheckoutCustomer checkoutCustomer, FindBill findBill) {
        this.addCustomer = addCustomer;
        this.findCustomer = findCustomer;
        this.customerAssembler = customerAssembler;
        this.ordersAssembler = ordersAssembler;
        this.orderCoffee = orderCoffee;
        this.findOrders = findOrders;
        this.checkoutCustomer = checkoutCustomer;
        this.findBill = findBill;
    }

    @GET
    @Path("/customers/{CUSTOMER_ID}")
    public Response getCustomer(@PathParam("CUSTOMER_ID") String customerId) {
        CustomerDTO customer = findCustomer.findById(new CustomerId(customerId));

        return Response.status(200).entity(customerAssembler.toCustomerResponse(customer)).build();
    }

    @POST
    @Path("/check-in")
    public Response addCustomer(CheckInRequest checkInRequest) {
        String customerId = checkInRequest.customerId();
        String customerName = checkInRequest.customerName();
        String groupName = checkInRequest.groupName();

        addCustomer.addCustomerToSeat(new CustomerId(customerId), customerName, groupName);

        return Response.created(URI.create("customers/" + customerId)).build();
    }

    @POST
    @Path("/checkout")
    public Response checkout(CheckoutRequest checkOutRequest) {
        String customerId = checkOutRequest.customerId();
        checkoutCustomer.checkout(new CustomerId(customerId));

        return Response.created(URI.create("customers/" + customerId + "/bill")).build();
    }

    @PUT
    @Path("/customers/{CUSTOMER_ID}/orders")
    public Response order(@PathParam("CUSTOMER_ID") String customerId, OrdersRequest ordersRequest) {
        orderCoffee.order(customerId, ordersRequest.orders());

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/customers/{CUSTOMER_ID}/orders")
    public Response getOrders(@PathParam("CUSTOMER_ID") String customerId) {
        OrderDTO orderDTO = findOrders.find(customerId);

        return Response.status(Response.Status.OK).entity(ordersAssembler.toOrderResponse(orderDTO)).build();
    }

    @GET
    @Path("/customers/{CUSTOMER_ID}/bill")
    public Response getBill(@PathParam("CUSTOMER_ID") String customerId) {
        BillDTO billDTO = findBill.find(customerId);
        BillResponse billResponse = new BillResponse(billDTO.order().getOrders(), billDTO.subtotal().roundUp(),
            billDTO.tax().roundUp(), billDTO.tip().roundUp(), billDTO.total().roundUp());

        return Response.status(Response.Status.OK).entity(billResponse).build();
    }
}
