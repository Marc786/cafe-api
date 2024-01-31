package ca.ulaval.glo4002.cafe.domain.tax;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RateFactoryTest {

    private final static String VALID_STATE_CA = "QC";
    private final static String VALID_STATE_US = "TX";
    private final static String INVALID_STATE = "PQ";
    private final static String ANY_STATE = "asd";
    private final static String VALID_COUNTRY_US = "US";
    private final static String VALID_COUNTRY_CA = "CA";
    private final static String VALID_COUNTRY_CHILE = "CL";
    private final static String VALID_COUNTRY_NONE = "None";
    private final static String INVALID_COUNTRY = "INVALID";
    private final static double VALID_COUNTRY_RATE_CA = 0.05;
    private final static double VALID_STATE_RATE_CA = 0.09975;
    private final static double VALID_STATE_RATE_US = 0.0625;

    private RateFactory rateFactory;

    @BeforeEach
    void createFactory() {
        rateFactory = new RateFactory();
    }

    @Test
    void validCountry_createRate_returnExpectedRate() {
        Rate rate = rateFactory.createCountryRate(VALID_COUNTRY_CA);

        assertThat(rate.getDoubleValue()).isEqualTo(VALID_COUNTRY_RATE_CA);
    }

    @Test
    void invalidCountry_createRate_shouldThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> rateFactory.createCountryRate(INVALID_COUNTRY));
    }

    @Test
    void CanadaAndValidProvince_createStateRate_returnExpectedRate() {
        Rate rate = rateFactory.createStateRate(VALID_COUNTRY_CA, VALID_STATE_CA);

        assertThat(rate.getDoubleValue()).isEqualTo(VALID_STATE_RATE_CA);
    }

    @Test
    void USAndValidProvince_createStateRate_returnExpectedRate() {
        Rate rate = rateFactory.createStateRate(VALID_COUNTRY_US, VALID_STATE_US);

        assertThat(rate.getDoubleValue()).isEqualTo(VALID_STATE_RATE_US);
    }

    @Test
    void NoneCountry_createStateRate_returnRateZero() {
        Rate expectedRate = new Rate(0);
        Rate rate = rateFactory.createStateRate(VALID_COUNTRY_NONE, ANY_STATE);

        assertThat(rate).isEqualTo(expectedRate);
    }

    @Test
    void invalidCountry_createStateRate_throwsInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> rateFactory.createStateRate(INVALID_COUNTRY, ANY_STATE));
    }

    @Test
    void ChileCountry_createStateRate_returnRateZero() {
        Rate expectedRate = new Rate(0);
        Rate rate = rateFactory.createStateRate(VALID_COUNTRY_CHILE, ANY_STATE);

        assertThat(rate).isEqualTo(expectedRate);
    }

    @Test
    void invalidCountryAndState_createStateRate_shouldThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> rateFactory.createStateRate(VALID_COUNTRY_CA, INVALID_STATE));
    }
}
