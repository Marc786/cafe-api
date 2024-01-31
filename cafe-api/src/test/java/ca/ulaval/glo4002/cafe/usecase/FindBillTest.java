package ca.ulaval.glo4002.cafe.usecase;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.Price;
import ca.ulaval.glo4002.cafe.domain.tax.Bill;
import ca.ulaval.glo4002.cafe.usecase.dto.BillDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.OrderDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class FindBillTest {

    private static final String CUSTOMER_ID_STRING = "123abc";
    private static final CustomerId CUSTOMER_ID = new CustomerId(CUSTOMER_ID_STRING);
    private static final Price SUBTOTAL = new Price(10);
    private static final Price TAX = new Price(1);
    private static final Price TIP = new Price(2);
    private static final Price TOTAL = new Price(13);

    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final Customer customerMock = mock(Customer.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private FindBill findBill;

    @BeforeEach
    public void createFindCustomer() {
        findBill = new FindBill(cafeRepositoryMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void customerIdWithABill_findBill_expectedBillDTOIsCreated() {
        when(cafeMock.findCustomerById(CUSTOMER_ID)).thenReturn(customerMock);
        when(customerMock.tryGetBill()).thenReturn(createBill());
        BillDTO expectedBillDTO = createBillDTO();

        BillDTO billDTO = findBill.find(CUSTOMER_ID_STRING);

        assertThat(billDTO).isEqualTo(expectedBillDTO);
    }

    private Bill createBill() {
        return new Bill(SUBTOTAL, TAX, TIP, TOTAL);
    }

    private BillDTO createBillDTO() {
        return new BillDTO(SUBTOTAL, TAX, TIP, TOTAL, new OrderDTO(new ArrayList<>()));
    }
}
