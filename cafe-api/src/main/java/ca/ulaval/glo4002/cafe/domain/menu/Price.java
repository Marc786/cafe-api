package ca.ulaval.glo4002.cafe.domain.menu;

import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Price {

    private final BigDecimal price;

    public Price(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    public Price(BigDecimal price) {
        this.price = price;
    }

    public double getDoubleValue() {
        return price.doubleValue();
    }

    public BigDecimal getValue() {
        return this.price;
    }

    public Price add(Price price) {
        return new Price(this.price.add(price.getValue()));
    }

    public Price multiply(Rate rate) {
        return new Price(this.price.multiply(rate.getValue()));
    }

    public int compareTo(Price price) {
        return this.price.compareTo(price.getValue());
    }

    public BigDecimal roundUp() {
        return price.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
