package ca.ulaval.glo4002.cafe.api.rest.cafe;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeResponse;
import ca.ulaval.glo4002.cafe.api.rest.seat.SeatResponse;
import ca.ulaval.glo4002.cafe.domain.customer.CustomerId;
import ca.ulaval.glo4002.cafe.domain.seat.SeatId;
import ca.ulaval.glo4002.cafe.domain.seat.Status;
import ca.ulaval.glo4002.cafe.usecase.dto.CafeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.usecase.dto.SeatDTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CafeAssemblerTest {

    private static final String CAFE_NAME = "Les 4-Fées";
    private static final String CUBE_NAME = "Cube";
    private static final String GROUP_NAME = "les yos";
    private static final SeatId SEAT_ID = new SeatId(1);
    private static final CustomerId CUSTOMER_ID = new CustomerId("123-456");

    @Test
    void cafe_convertToCafeResponse_returnsExpectedCafeResponse() {
        CafeAssembler cafeAssembler = new CafeAssembler();
        CafeDTO cafe = new CafeDTO(CAFE_NAME, createCubes());
        List<CubeResponse> expectedCubeResponses = createCubeResponses();

        CafeResponse cafeResponse = cafeAssembler.toCafeResponse(cafe);

        assertThat(cafeResponse.name()).isEqualTo(CAFE_NAME);
        assertThat(cafeResponse.cubes()).isEqualTo(expectedCubeResponses);
    }

    private List<CubeResponse> createCubeResponses() {
        List<CubeResponse> cubesResponse = new ArrayList<>();
        cubesResponse.add(new CubeResponse(CUBE_NAME, createSeatResponses()));
        return cubesResponse;
    }

    private List<CubeDTO> createCubes() {
        List<CubeDTO> cubes = new ArrayList<>();
        cubes.add(new CubeDTO(CUBE_NAME, createSeats()));
        return cubes;
    }

    private List<SeatResponse> createSeatResponses() {
        List<SeatResponse> seatsResponse = new ArrayList<>();
        seatsResponse.add(
            new SeatResponse(SEAT_ID.getValue(), Status.AVAILABLE.status, CUSTOMER_ID.getValue(), GROUP_NAME));

        return seatsResponse;
    }

    private List<SeatDTO> createSeats() {
        List<SeatDTO> seats = new ArrayList<>();
        SeatDTO seat = new SeatDTO(SEAT_ID, Status.AVAILABLE, CUSTOMER_ID, GROUP_NAME);
        seats.add(seat);

        return seats;
    }
}
