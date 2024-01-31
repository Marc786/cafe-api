package ca.ulaval.glo4002.cafe.infra.dto;

import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;

import java.util.Objects;

public class InMemoryStrategy {

    private final ReservationType reservationType;

    public InMemoryStrategy(ReservationType reservationType) {
        this.reservationType = reservationType;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryStrategy that = (InMemoryStrategy) o;
        return reservationType == that.reservationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationType);
    }
}
