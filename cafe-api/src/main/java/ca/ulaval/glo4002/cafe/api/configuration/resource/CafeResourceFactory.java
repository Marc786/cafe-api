package ca.ulaval.glo4002.cafe.api.configuration.resource;

import ca.ulaval.glo4002.cafe.api.rest.cafe.CafeAssembler;
import ca.ulaval.glo4002.cafe.api.rest.cafe.CafeResource;
import ca.ulaval.glo4002.cafe.api.rest.inventory.InventoryAssembler;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.config.CafeConfigFactory;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationStrategyFactory;
import ca.ulaval.glo4002.cafe.usecase.AddToInventory;
import ca.ulaval.glo4002.cafe.usecase.ChangeReservationConfig;
import ca.ulaval.glo4002.cafe.usecase.CloseCafe;
import ca.ulaval.glo4002.cafe.usecase.FindCafe;
import ca.ulaval.glo4002.cafe.usecase.FindInventory;
import ca.ulaval.glo4002.cafe.usecase.assembler.CafeDTOAssembler;

public class CafeResourceFactory {

    private final ChangeReservationConfig changeReservationConfig;
    private final CloseCafe closeCafe;
    private final FindCafe findCafe;
    private final FindInventory findInventory;
    private final AddToInventory addToInventory;

    public CafeResourceFactory(ReservationStrategyFactory reservationStrategyFactory, CafeRepository cafeRepository,
        CafeFactory cafeFactory) {
        CafeConfigFactory cafeConfigFactory = new CafeConfigFactory(reservationStrategyFactory);
        CafeDTOAssembler cafeDTOAssembler = new CafeDTOAssembler();
        this.changeReservationConfig = new ChangeReservationConfig(cafeRepository, cafeConfigFactory, cafeFactory);
        this.closeCafe = new CloseCafe(cafeRepository);
        this.findCafe = new FindCafe(cafeRepository, cafeDTOAssembler);
        this.findInventory = new FindInventory(cafeRepository);
        this.addToInventory = new AddToInventory(cafeRepository);
    }

    public CafeResource create() {
        CafeAssembler cafeAssembler = new CafeAssembler();
        InventoryAssembler inventoryAssembler = new InventoryAssembler();
        return new CafeResource(inventoryAssembler, findCafe, closeCafe, changeReservationConfig, cafeAssembler,
            findInventory, addToInventory);
    }
}
