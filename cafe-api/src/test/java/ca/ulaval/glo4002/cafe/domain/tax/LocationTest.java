package ca.ulaval.glo4002.cafe.domain.tax;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;

import org.junit.jupiter.api.Test;

class LocationTest {

    private final static String COUNTRY_CANADA = "CA";
    private final static String COUNTRY_US = "US";
    private final static String COUNTRY_CHILE = "CL";
    private final static String COUNTRY_NONE = "None";
    private final static String COUNTRY_INVALID = "Quebec";
    private final static String PROVINCE = "une province";
    private final static String STATE = "une state";

    @Test
    void countryCanada_createNewLocation_LocationCreatedWithProvince() {
        Location location = new Location(COUNTRY_CANADA, PROVINCE, STATE);

        assertThat(location.getStateOrProvince()).isEqualTo(PROVINCE);
    }

    @Test
    void countryUS_createNewLocation_LocationCreatedWithState() {
        Location location = new Location(COUNTRY_US, PROVINCE, STATE);

        assertThat(location.getStateOrProvince()).isEqualTo(STATE);
    }

    @Test
    void countryChile_createNewLocation_LocationCreatedWithEmptyStateOrProvince() {
        Location location = new Location(COUNTRY_CHILE, PROVINCE, STATE);

        assertThat(location.getStateOrProvince()).isEmpty();
    }

    @Test
    void countryNone_createNewLocation_LocationCreatedWithEmptyStateOrProvince() {
        Location location = new Location(COUNTRY_NONE, PROVINCE, STATE);

        assertThat(location.getStateOrProvince()).isEmpty();
    }

    @Test
    void invalidCountry_createNewLocation_shouldThrowInvalidCountryException() {
        assertThrows(InvalidCountryException.class, () -> new Location(COUNTRY_INVALID, PROVINCE, STATE));
    }
}
