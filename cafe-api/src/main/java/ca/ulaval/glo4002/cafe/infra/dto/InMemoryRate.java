package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.Objects;

public class InMemoryRate {

    private final double rate;

    public InMemoryRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryRate that = (InMemoryRate) o;
        return Double.compare(that.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
