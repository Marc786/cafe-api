package ca.ulaval.glo4002.cafe.domain.customer;

import java.util.Objects;

public class CustomerId {

    private final String id;

    public CustomerId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerId that = (CustomerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getValue() {
        return id;
    }
}
