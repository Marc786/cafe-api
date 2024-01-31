package ca.ulaval.glo4002.cafe.usecase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exception.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoReservationsFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AddCustomerTest {

    private static final CustomerId CUSTOMER_ID = new CustomerId(UUID.randomUUID().toString());
    private static final String CUSTOMER_NAME = "name";
    private static final String GROUP_NAME = "ungroup";
    private static final String NO_GROUP_NAME = null;
    private final CafeRepository cafeRepositoryMock = mock(CafeRepository.class);
    private final CustomerFactory customerFactoryMock = mock(CustomerFactory.class);
    private final Cafe cafeMock = mock(Cafe.class);
    private AddCustomer addCustomer;

    @BeforeEach
    void setup() {
        addCustomer = new AddCustomer(cafeRepositoryMock, customerFactoryMock);
        when(cafeRepositoryMock.fetchCafe()).thenReturn(cafeMock);
    }

    @Test
    void customerAlreadyInsideCafe_addCustomer_throwDuplicateCustomerExceptionAndStopExecution() {
        when(cafeMock.isCustomerAlreadyInside(any())).thenReturn(true);

        assertThrows(DuplicateCustomerIdException.class,
            () -> addCustomer.addCustomerToSeat(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME));
    }

    @Test
    void customerNotInCafeAndWithExistingGroup_addCustomer_createCustomerAndAddedToGroupInCafe() {
        when(cafeMock.isCustomerAlreadyInside(CUSTOMER_ID)).thenReturn(false);
        when(cafeMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(true);

        addCustomer.addCustomerToSeat(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME);

        verify(cafeMock).addCustomerInGroup(any(), eq(GROUP_NAME));
        verify(cafeRepositoryMock).save(cafeMock);
    }

    @Test
    void customerNotInCafeAndWithInvalidGroup_addCustomer_throwNoReservationsFoundException() {
        when(cafeMock.isCustomerAlreadyInside(CUSTOMER_ID)).thenReturn(false);
        when(cafeMock.doesReservationAlreadyExist(GROUP_NAME)).thenReturn(false);

        assertThrows(NoReservationsFoundException.class,
            () -> addCustomer.addCustomerToSeat(CUSTOMER_ID, CUSTOMER_NAME, GROUP_NAME));
    }

    @Test
    void customerNotInCafeWithoutGroup_addCustomer_createCustomerAndAddedToCafe() {
        when(cafeMock.isCustomerAlreadyInside(CUSTOMER_ID)).thenReturn(false);

        addCustomer.addCustomerToSeat(CUSTOMER_ID, CUSTOMER_NAME, NO_GROUP_NAME);

        verify(cafeMock).addCustomer(any());
        verify(cafeRepositoryMock).save(cafeMock);
    }
}
