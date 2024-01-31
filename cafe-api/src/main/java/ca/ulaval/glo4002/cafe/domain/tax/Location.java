package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;
import ca.ulaval.glo4002.cafe.domain.tax.country.Country;

import java.util.Objects;

public class Location {

    private final String country;
    private final String stateProvince;

    public Location(String country, String province, String state) {
        this.country = country;
        this.stateProvince = validateStateOrProvince(country, province, state);
    }

    public String getCountry() {
        return country;
    }

    public String getStateOrProvince() {
        return stateProvince;
    }

    private String validateStateOrProvince(String country, String province, String state) {
        if (Country.CA.name.equals(country)) {
            return province;
        } else if (Country.US.name.equals(country)) {
            return state;
        } else if (Country.CL.name.equals(country) || Country.NONE.name.equals(country)) {
            return "";
        } else
            throw new InvalidCountryException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Location location = (Location) o;
        return Objects.equals(country, location.country) && Objects.equals(stateProvince, location.stateProvince);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, stateProvince);
    }
}
