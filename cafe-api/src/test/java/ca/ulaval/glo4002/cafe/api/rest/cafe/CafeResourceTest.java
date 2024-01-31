package ca.ulaval.glo4002.cafe.api.rest.cafe;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeResponse;
import ca.ulaval.glo4002.cafe.api.rest.inventory.AddInventoryRequest;
import ca.ulaval.glo4002.cafe.api.rest.inventory.InventoryAssembler;
import ca.ulaval.glo4002.cafe.api.rest.seat.SeatResponse;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.menu.MenuIngredient;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.domain.tax.Location;
import ca.ulaval.glo4002.cafe.usecase.AddToInventory;
import ca.ulaval.glo4002.cafe.usecase.ChangeReservationConfig;
import ca.ulaval.glo4002.cafe.usecase.CloseCafe;
import ca.ulaval.glo4002.cafe.usecase.FindCafe;
import ca.ulaval.glo4002.cafe.usecase.FindInventory;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.IngredientDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.SeatDTO;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CafeResourceTest {

    private static final ReservationType RESERVATION_TYPE = ReservationType.DEFAULT;
    private static final String CAFE_NAME = "cafeeee";
    private static final String CUBE_NAME = "cubename";
    private static final String GROUP_NAME = "thebest";
    private static final String COUNTRY = "CA";
    private static final String PROVINCE = "QC";
    private static final String STATE = "";
    private static final SeatId SEAT_ID = new SeatId(1);
    private static final Status STATUS = Status.AVAILABLE;
    private static final CustomerId CUSTOMER_ID = new CustomerId("ginette");
    private static final String RESERVATION_METHOD = "Default";
    private static final String ORGANIZATION_NAME = "banane";
    private static final int CUBE_SIZE = 4;
    private static final double TIP = 0;
    private static final Location VALID_LOCATION = new Location(COUNTRY, PROVINCE, STATE);
    private static final String INGREDIENT_NAME_1 = "lait";
    private static final String INGREDIENT_NAME_2 = "farine";
    private static final int QUANTITY_1 = 3;
    private static final int QUANTITY_2 = 4;
    private static final int CHOCOLATE_QUANTITY = 12;
    private static final int ESPRESSO_QUANTITY = 3;
    private static final int MILK_QUANTITY = 4;
    private static final int WATER_QUANTITY = 30;

    private final FindCafe findCafe = mock(FindCafe.class);
    private final CloseCafe closeCafe = mock(CloseCafe.class);
    private final ChangeReservationConfig changeReservationConfig = mock(ChangeReservationConfig.class);
    private final FindInventory findInventory = mock(FindInventory.class);
    private final AddToInventory addToInventory = mock(AddToInventory.class);
    private final CafeAssembler cafeAssembler = new CafeAssembler();
    private final InventoryAssembler inventoryAssembler = new InventoryAssembler();

    private CafeResource cafeResource;

    @BeforeEach
    void createCafeResource() {
        cafeResource = new CafeResource(inventoryAssembler, findCafe, closeCafe, changeReservationConfig, cafeAssembler,
            findInventory, addToInventory);
    }

    @Test
    void getLayout_returnsExpectedResponse() {
        when(findCafe.find()).thenReturn(createCafeDTO());
        CafeResponse expectedCafeResponse = createCafeResponse();

        Response response = cafeResource.getLayout();

        assertThat(response.getEntity()).isEqualTo(expectedCafeResponse);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cafeConfigRequest_changeConfig_responseStatusIs200() {
        CafeConfigRequest cafeConfigRequest = new CafeConfigRequest(RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, PROVINCE, STATE, TIP);

        Response response = cafeResource.changeConfig(cafeConfigRequest);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cafeConfigRequest_changeConfig_changeReservationConfigIsCalled() {
        CafeConfigRequest cafeConfigRequest = new CafeConfigRequest(RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, PROVINCE, STATE, TIP);

        cafeResource.changeConfig(cafeConfigRequest);

        verify(changeReservationConfig).change(RESERVATION_TYPE, ORGANIZATION_NAME, CUBE_SIZE, VALID_LOCATION, TIP);
    }

    @Test
    void close_responseStatusIs200() {
        Response response = cafeResource.close();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void close_closeCafeIsCalled() {
        cafeResource.close();

        verify(closeCafe).close();
    }

    @Test
    void getInventory_returnsExpectedResponse() {
        when(findInventory.find()).thenReturn(createIngredientsDTO());
        Map<String, Integer> expectedIngredientsResponse = createIngredientsResponse();

        Response response = cafeResource.getInventory();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(expectedIngredientsResponse);
    }

    @Test
    void inventoryRequest_addToInventory_addInventoryIsCalled() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest(CHOCOLATE_QUANTITY, ESPRESSO_QUANTITY,
            MILK_QUANTITY, WATER_QUANTITY);
        List<MenuIngredient> menuIngredients = createIngredients(addInventoryRequest);

        cafeResource.addToInventory(addInventoryRequest);

        verify(addToInventory).add(menuIngredients);
    }

    @Test
    void inventoryRequest_addToInventory_returnsExpectedResponse() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest(CHOCOLATE_QUANTITY, ESPRESSO_QUANTITY,
            MILK_QUANTITY, WATER_QUANTITY);

        Response response = cafeResource.addToInventory(addInventoryRequest);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    private Map<String, Integer> createIngredientsResponse() {
        Map<String, Integer> ingredientsResponse = new HashMap<>();
        ingredientsResponse.put(INGREDIENT_NAME_1, QUANTITY_1);
        ingredientsResponse.put(INGREDIENT_NAME_2, QUANTITY_2);
        return ingredientsResponse;
    }

    private List<IngredientDTO> createIngredientsDTO() {
        return List.of(new IngredientDTO(INGREDIENT_NAME_1, QUANTITY_1), new IngredientDTO(INGREDIENT_NAME_2,
            QUANTITY_2));
    }

    private CafeDTO createCafeDTO() {
        List<CubeDTO> cubesDTO = createCubesDTO();

        return new CafeDTO(CAFE_NAME, cubesDTO);
    }

    private List<CubeDTO> createCubesDTO() {
        List<CubeDTO> cubesDTO = new ArrayList<>();
        CubeDTO cubeDTO = new CubeDTO(CUBE_NAME, createSeatsDTO());
        cubesDTO.add(cubeDTO);
        return cubesDTO;
    }

    private List<MenuIngredient> createIngredients(AddInventoryRequest addInventoryRequest) {
        return inventoryAssembler.fromInventoryRequestToIngredients(addInventoryRequest);
    }

    private List<SeatDTO> createSeatsDTO() {
        List<SeatDTO> seatsDTO = new ArrayList<>();
        SeatDTO seatDTO = new SeatDTO(SEAT_ID, STATUS, CUSTOMER_ID, GROUP_NAME);
        seatsDTO.add(seatDTO);
        return seatsDTO;
    }

    private CafeResponse createCafeResponse() {
        List<CubeResponse> cubeResponses = createCubeResponses();

        return new CafeResponse(CAFE_NAME, cubeResponses);
    }

    private List<CubeResponse> createCubeResponses() {
        List<CubeResponse> cubeResponses = new ArrayList<>();
        CubeResponse cubeResponse = new CubeResponse(CUBE_NAME, createSeatResponses());
        cubeResponses.add(cubeResponse);
        return cubeResponses;
    }

    private List<SeatResponse> createSeatResponses() {
        List<SeatResponse> seatResponses = new ArrayList<>();
        SeatResponse seatResponse = new SeatResponse(SEAT_ID.getValue(), STATUS.status, CUSTOMER_ID.getValue(),
            GROUP_NAME);
        seatResponses.add(seatResponse);
        return seatResponses;
    }
}
