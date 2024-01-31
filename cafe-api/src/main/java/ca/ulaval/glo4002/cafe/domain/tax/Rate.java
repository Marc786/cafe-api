package ca.ulaval.glo4002.cafe.domain.tax;

import java.math.BigDecimal;
import java.util.Objects;

public class Rate {

    private final BigDecimal rate;

    public Rate(double rate) {
        this.rate = BigDecimal.valueOf(rate);
    }

    public Rate(BigDecimal rate) {
        this.rate = rate;
    }

    public double getDoubleValue() {
        return rate.doubleValue();
    }

    public BigDecimal getValue() {
        return rate;
    }

    public Rate add(Rate rate) {
        return new Rate(this.rate.add(rate.getValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rate rate1 = (Rate) o;
        return Objects.equals(rate, rate1.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
