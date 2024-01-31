package ca.ulaval.glo4002.cafe.usecase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckoutCustomerTest {

    private static final String CUSTOMER_ID = "123";
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private CheckoutCustomer checkoutCustomer;

    @BeforeEach
    public void createFindCustomer() {
        checkoutCustomer = new CheckoutCustomer(cafeRepositoryMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void customerId_checkoutCustomer_customerIsCheckedOut() {
        CustomerId customerId = new CustomerId(CUSTOMER_ID);

        checkoutCustomer.checkout(customerId);

        verify(cafeMock).checkoutCustomer(customerId);
        verify(cafeRepositoryMock).save(cafeMock);
    }
}
