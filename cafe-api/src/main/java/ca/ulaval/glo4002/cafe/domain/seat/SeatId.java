package ca.ulaval.glo4002.cafe.domain.seat;

import java.util.Objects;

public class SeatId {

    private final int id;

    public SeatId(int number) {
        this.id = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SeatId seatId = (SeatId) o;
        return id == seatId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getValue() {
        return id;
    }
}
