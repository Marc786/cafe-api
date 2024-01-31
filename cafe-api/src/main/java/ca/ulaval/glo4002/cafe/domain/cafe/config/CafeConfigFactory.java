package ca.ulaval.glo4002.cafe.domain.cafe.config;

import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.tax.Location;
import ca.ulaval.glo4002.cafe.domain.tax.Rate;
import ca.ulaval.glo4002.cafe.domain.tax.RateFactory;

public class CafeConfigFactory {

    private final ReservationStrategyFactory reservationStrategyFactory;
    private final RateFactory rateFactory = new RateFactory();


    public CafeConfigFactory(ReservationStrategyFactory reservationStrategyFactory) {
        this.reservationStrategyFactory = reservationStrategyFactory;
    }

    public CafeConfig create(ReservationType reservationType, String organizationName, int cubeSize, Location location,
        double tip) {
        ReservationStrategy reservationStrategy = reservationStrategyFactory.create(reservationType);
        Rate countryRate = rateFactory.createCountryRate(location.getCountry());
        Rate stateRate = rateFactory.createStateRate(location.getCountry(), location.getStateOrProvince());
        Rate tipRate = rateFactory.createTipRate(tip);

        return new CafeConfig(reservationStrategy, organizationName, cubeSize, countryRate, stateRate, tipRate);
    }
}
