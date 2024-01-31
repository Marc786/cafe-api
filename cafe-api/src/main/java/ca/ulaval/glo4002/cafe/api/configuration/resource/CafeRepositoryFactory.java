package ca.ulaval.glo4002.cafe.api.configuration.resource;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.reservationStrategyImp.DefaultStrategy;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.infra.InMemoryCafeRepository;
import ca.ulaval.glo4002.cafe.infra.assembler.InMemoryCafeAssembler;

public class CafeRepositoryFactory {

    private final Cafe cafe;
    private final InMemoryCafeAssembler inMemoryCafeAssembler;

    public CafeRepositoryFactory(CafeFactory cafeFactory, ReservationStrategyFactory reservationStrategyFactory) {
        String cafeName = "Les 4-Fées";
        int cubeSize = 4;
        CafeConfig cafeConfig = new CafeConfig(new DefaultStrategy(), cafeName, cubeSize, new Rate(0), new Rate(0),
            new Rate(0));
        this.cafe = cafeFactory.create(cafeConfig);
        this.inMemoryCafeAssembler = new InMemoryCafeAssembler(reservationStrategyFactory);
    }

    public CafeRepository create() {
        return new InMemoryCafeRepository(cafe, inMemoryCafeAssembler);
    }
}
