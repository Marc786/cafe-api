package ca.ulaval.glo4002.cafe.api.configuration.resource;

import ca.ulaval.glo4002.cafe.api.rest.cafe.CafeResource;
import ca.ulaval.glo4002.cafe.api.rest.customer.CustomerResource;
import ca.ulaval.glo4002.cafe.api.rest.reservation.ReservationResource;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;

import org.glassfish.jersey.server.ResourceConfig;

public class ResourceConfigFactory {

    public ResourceConfig create() {
        CafeFactory cafeFactory = new CafeFactory();
        ReservationStrategyFactory reservationStrategyFactory = new ReservationStrategyFactory();

        CafeRepositoryFactory cafeRepositoryFactory = new CafeRepositoryFactory(cafeFactory,
            reservationStrategyFactory);
        CafeRepository cafeRepository = cafeRepositoryFactory.create();

        ReservationResourceFactory reservationResourceFactory = new ReservationResourceFactory(cafeRepository);
        ReservationResource reservationResource = reservationResourceFactory.create();

        CustomerResourceFactory customerResourceFactory = new CustomerResourceFactory(cafeRepository);
        CustomerResource customerResource = customerResourceFactory.create();

        CafeResourceFactory cafeResourceFactory = new CafeResourceFactory(reservationStrategyFactory, cafeRepository,
            cafeFactory);
        CafeResource cafeResource = cafeResourceFactory.create();

        String packageName = "ca.ulaval.glo4002.cafe";

        return new ResourceConfig()
            .packages(packageName)
            .register(cafeResource)
            .register(customerResource)
            .register(reservationResource);
    }
}
