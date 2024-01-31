package ca.ulaval.glo4002.cafe.usecase;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.usecase.assembler.CustomerDTOAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class FindCustomerTest {

    private static final CustomerId CUSTOMER_ID = new CustomerId("123abc");
    private static final String CUSTOMER_NAME = "name";
    private static final SeatId SEAT_ID = new SeatId(1);
    private static final String GROUP_NAME = "groupeDeFeu";
    private static final Seat CUSTOMER_SEAT_WITH_GROUP = new Seat(SEAT_ID, Status.OCCUPIED, CUSTOMER_ID, GROUP_NAME);

    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final CustomerDTOAssembler customerAssemblerMock = mock(CustomerDTOAssembler.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private FindCustomer findCustomer;

    @BeforeEach
    public void createFindCustomer() {
        findCustomer = new FindCustomer(cafeRepositoryMock, customerAssemblerMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void customerWithGroup_findCustomer_toCustomerDTOWithGroupIsCalled() {
        Customer customer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, Collections.emptyList());
        when(cafeMock.findCustomerSeat(CUSTOMER_ID)).thenReturn(CUSTOMER_SEAT_WITH_GROUP);
        when(cafeMock.findCustomerById(CUSTOMER_ID)).thenReturn(customer);

        findCustomer.findById(CUSTOMER_ID);

        verify(customerAssemblerMock).toCustomerDTOWithGroup(CUSTOMER_NAME, SEAT_ID, GROUP_NAME);
    }
}
