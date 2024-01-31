package ca.ulaval.glo4002.cafe.usecase;

import static java.util.Objects.isNull;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.customer.Customer;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerFactory;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.customer.exception.DuplicateCustomerIdException;
import ca.ulaval.glo4002.cafe.domain.reservation.exception.NoReservationsFoundException;

public class AddCustomer {

    private final CafeRepository cafeRepository;
    private final CustomerFactory customerFactory;

    public AddCustomer(CafeRepository cafeRepository, CustomerFactory customerFactory) {
        this.cafeRepository = cafeRepository;
        this.customerFactory = customerFactory;
    }

    public void addCustomerToSeat(CustomerId customerId, String customerName, String groupName) {
        Cafe cafe = cafeRepository.fetchCafe();

        if (cafe.isCustomerAlreadyInside(customerId))
            throw new DuplicateCustomerIdException();

        Customer customer = customerFactory.create(customerId, customerName);
        addCustomer(groupName, cafe, customer);

        cafeRepository.save(cafe);
    }

    private void addCustomer(String groupName, Cafe cafe, Customer customer) {
        if (isCustomerInAGroup(groupName)) {
            addCustomerWithGroup(groupName, cafe, customer);
        } else
            cafe.addCustomer(customer);
    }

    private void addCustomerWithGroup(String groupName, Cafe cafe, Customer customer) {
        validateReservationExists(cafe, groupName);
        cafe.addCustomerInGroup(customer, groupName);
    }

    private boolean isCustomerInAGroup(String groupName) {
        return !isNull(groupName);
    }

    private void validateReservationExists(Cafe cafe, String groupName) {
        if (!cafe.doesReservationAlreadyExist(groupName))
            throw new NoReservationsFoundException();
    }
}
