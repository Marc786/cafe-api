package ca.ulaval.glo4002.cafe.api.rest.customer;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.customer.dto.BillResponse;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CheckInRequest;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CheckoutRequest;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.CustomerResponse;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.OrdersRequest;
import ca.ulaval.glo4002.cafe.api.rest.customer.dto.OrdersResponse;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.usecase.AddCustomer;
import ca.ulaval.glo4002.cafe.usecase.CheckoutCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindBill;
import ca.ulaval.glo4002.cafe.usecase.FindCustomer;
import ca.ulaval.glo4002.cafe.usecase.FindOrders;
import ca.ulaval.glo4002.cafe.usecase.OrderCoffee;
import ca.ulaval.glo4002.cafe.usecase.dto.BillDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.CustomerDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CustomerResourceTest {

    private static final String CUSTOMER_ID_STRING = "POLO";
    private static final CustomerId CUSTOMER_ID = new CustomerId(CUSTOMER_ID_STRING);
    private static final int SEAT_NUMBER = 45;
    private static final String GROUP_NAME = "les poils";
    private static final String CUSTOMER_NAME = "marco";
    private static final List<String> ORDERS = List.of("Mocha", "Latte");

    private final AddCustomer addCustomer = mock(AddCustomer.class);
    private final FindCustomer findCustomer = mock(FindCustomer.class);
    private final OrderCoffee orderCoffee = mock(OrderCoffee.class);
    private final FindOrders findOrders = mock(FindOrders.class);
    private final CheckoutCustomer checkoutCustomer = mock(CheckoutCustomer.class);
    private final FindBill findBill = mock(FindBill.class);
    private final CustomerAssembler customerAssembler = new CustomerAssembler();
    private final OrdersAssembler ordersAssembler = new OrdersAssembler();
    private CustomerResource customerResource;

    @BeforeEach
    void createCustomerResource() {
        customerResource = new CustomerResource(addCustomer, findCustomer, customerAssembler, ordersAssembler,
            orderCoffee, findOrders, checkoutCustomer, findBill);
    }

    @Test
    void customerId_getCustomer_returnsExpectedResponse() {
        when(findCustomer.findById(CUSTOMER_ID)).thenReturn(createCustomerDTO());
        CustomerResponse expectedCustomerResponse = createCustomerResponse();

        Response response = customerResource.getCustomer(CUSTOMER_ID_STRING);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(expectedCustomerResponse);
    }

    @Test
    void checkInRequest_addCustomer_responseStatusIs200() {
        CheckInRequest checkInRequest = new CheckInRequest(CUSTOMER_ID_STRING, CUSTOMER_NAME, GROUP_NAME);

        Response response = customerResource.addCustomer(checkInRequest);

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    void checkInRequest_addCustomer_addCustomerToSeatIsCalled() {
        CheckInRequest checkInRequest = new CheckInRequest(CUSTOMER_ID_STRING, CUSTOMER_NAME, GROUP_NAME);

        customerResource.addCustomer(checkInRequest);

        verify(addCustomer).addCustomerToSeat(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME);
    }

    @Test
    void orderRequest_placeOrder_responseStatusIs200() {
        OrdersRequest ordersRequest = new OrdersRequest(ORDERS);

        Response response = customerResource.order(CUSTOMER_ID_STRING, ordersRequest);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void orderRequest_placeOrder_orderCoffeeOrderIsCalled() {
        OrdersRequest ordersRequest = new OrdersRequest(ORDERS);

        customerResource.order(CUSTOMER_ID_STRING, ordersRequest);

        verify(orderCoffee).order(CUSTOMER_ID_STRING, ORDERS);
    }

    @Test
    void customerId_getOrdersForCustomer_returnsExpectedResponse() {
        OrdersResponse expectedOrdersResponse = new OrdersResponse(ORDERS);
        when(findOrders.find(CUSTOMER_ID_STRING)).thenReturn(new OrderDTO(ORDERS));

        Response response = customerResource.getOrders(CUSTOMER_ID_STRING);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(expectedOrdersResponse);
    }

    @Test
    void checkoutRequest_checkout_checkoutIsCalled() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(CUSTOMER_ID_STRING);
        CustomerId customerId = new CustomerId(checkoutRequest.customerId());

        customerResource.checkout(checkoutRequest);

        verify(checkoutCustomer).checkout(customerId);
    }

    @Test
    void checkoutRequest_checkout_returnsExpectedResponse() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(CUSTOMER_ID_STRING);

        Response response = customerResource.checkout(checkoutRequest);

        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    void customerId_getBill_returnsExpectedResponse() {
        when(findBill.find(CUSTOMER_ID_STRING)).thenReturn(createBillDTO());
        BillResponse expectedBillResponse = createBillResponse();

        Response response = customerResource.getBill(CUSTOMER_ID_STRING);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(expectedBillResponse);
    }

    private BillResponse createBillResponse() {
        BillDTO billDTO = createBillDTO();
        return new BillResponse(billDTO.order().getOrders(), billDTO.subtotal().roundUp(), billDTO.tax().roundUp(),
            billDTO.tip().roundUp(), billDTO.total().roundUp());
    }

    private BillDTO createBillDTO() {
        Price subtotal = new Price(12);
        Price tax = new Price(2);
        Price tip = new Price(6);
        Price total = subtotal.add(tax).add(tip);
        OrderDTO order = new OrderDTO(ORDERS);
        return new BillDTO(subtotal, tax, tip, total, order);
    }

    private CustomerResponse createCustomerResponse() {
        return new CustomerResponse(CUSTOMER_ID_STRING, SEAT_NUMBER, GROUP_NAME);
    }

    private CustomerDTO createCustomerDTO() {
        SeatId seatId = new SeatId(SEAT_NUMBER);
        return new CustomerDTO(CUSTOMER_ID_STRING, seatId, GROUP_NAME);
    }
}
