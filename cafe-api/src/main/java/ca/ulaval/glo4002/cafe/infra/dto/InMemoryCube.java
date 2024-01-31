package ca.ulaval.glo4002.cafe.infra.dto;

import java.util.List;
import java.util.Objects;

public class InMemoryCube {

    private final String name;
    private final List<InMemorySeat> inMemorySeats;

    public InMemoryCube(String name, List<InMemorySeat> inMemorySeats) {
        this.name = name;
        this.inMemorySeats = inMemorySeats;
    }

    public String getName() {
        return name;
    }

    public List<InMemorySeat> getInMemorySeats() {
        return inMemorySeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryCube that = (InMemoryCube) o;
        return Objects.equals(name, that.name) && Objects.equals(inMemorySeats, that.inMemorySeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, inMemorySeats);
    }
}
