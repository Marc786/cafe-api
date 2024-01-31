package ca.ulaval.glo4002.cafe.infra;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.infra.assembler.InMemoryCafeAssembler;
import ca.ulaval.glo4002.cafe.infra.dto.InMemoryCafe;

public class InMemoryCafeRepository implements CafeRepository {

    private final InMemoryCafeAssembler inMemoryCafeAssembler;
    private InMemoryCafe inMemoryCafe;

    public InMemoryCafeRepository(InMemoryCafeAssembler inMemoryCafeAssembler) {
        this.inMemoryCafeAssembler = inMemoryCafeAssembler;
    }

    public InMemoryCafeRepository(Cafe cafe, InMemoryCafeAssembler inMemoryCafeAssembler) {
        this.inMemoryCafeAssembler = inMemoryCafeAssembler;
        save(cafe);
    }

    @Override
    public void save(Cafe cafe) {
        inMemoryCafe = inMemoryCafeAssembler.fromCafeToInMemoryCafe(cafe);
    }

    @Override
    public Cafe fetchCafe() {
        return inMemoryCafeAssembler.fromInMemoryCafeToCafe(inMemoryCafe);
    }
}
