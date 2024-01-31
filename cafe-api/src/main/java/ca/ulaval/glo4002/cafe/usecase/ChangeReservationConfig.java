package ca.ulaval.glo4002.cafe.usecase;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfigFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.tax.Location;

public class ChangeReservationConfig {

    private final CafeRepository cafeRepository;
    private final CafeConfigFactory cafeConfigFactory;
    private final CafeFactory cafeFactory;

    public ChangeReservationConfig(CafeRepository cafeRepository, CafeConfigFactory cafeConfigFactory,
        CafeFactory cafeFactory) {
        this.cafeRepository = cafeRepository;
        this.cafeConfigFactory = cafeConfigFactory;
        this.cafeFactory = cafeFactory;
    }

    public void change(ReservationType reservationType, String organizationName, int cubeSize, Location location,
        double tip) {
        CafeConfig cafeConfig = cafeConfigFactory.create(reservationType, organizationName, cubeSize, location, tip);
        Cafe cafe = cafeFactory.create(cafeConfig);
        cafeRepository.save(cafe);
    }
}
