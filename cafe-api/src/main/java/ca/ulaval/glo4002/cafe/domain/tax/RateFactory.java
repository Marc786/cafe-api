package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidCountryException;
import ca.ulaval.glo4002.cafe.domain.tax.country.Country;
import ca.ulaval.glo4002.cafe.domain.tax.state.CanadaProvince;
import ca.ulaval.glo4002.cafe.domain.tax.state.State;
import ca.ulaval.glo4002.cafe.domain.tax.state.USState;

import java.util.Arrays;

public class RateFactory {

    public Rate createCountryRate(String country) {
        return new Rate(findCountryRate(country));
    }

    public Rate createStateRate(String country, String state) {
        if (Country.CA.name.equals(country))
            return new Rate(findStateRate(state, CanadaProvince.values()));
        else if (Country.US.name.equals(country))
            return new Rate(findStateRate(state, USState.values()));
        else if (Country.CL.name.equals(country))
            return new Rate(0);
        else if (Country.NONE.name.equals(country))
            return new Rate(0);
        else
            throw new InvalidCountryException();
    }

    public Rate createTipRate(double tip) {
        return new Rate(tip);
    }

    private double findCountryRate(String country) {
        return Arrays.stream(Country.values())
            .filter(countryEnum -> countryEnum.name.equals(country))
            .findFirst()
            .map(countryEnum -> countryEnum.taxRate)
            .orElseThrow(InvalidCountryException::new);
    }

    private double findStateRate(String province, State<?>[] states) {
        return Arrays.stream(states)
            .filter(countryEnum -> countryEnum.getName().equals(province))
            .findFirst()
            .map(State::getRate)
            .orElseThrow(InvalidCountryException::new);
    }
}
