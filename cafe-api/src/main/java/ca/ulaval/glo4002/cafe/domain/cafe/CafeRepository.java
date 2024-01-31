package ca.ulaval.glo4002.cafe.domain.cafe;

public interface CafeRepository {

    void save(Cafe cafe);

    Cafe fetchCafe();
}
