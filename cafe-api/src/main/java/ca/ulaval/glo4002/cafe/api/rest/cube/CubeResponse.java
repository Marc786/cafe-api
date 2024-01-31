package ca.ulaval.glo4002.cafe.api.rest.cube;

import ca.ulaval.glo4002.cafe.api.rest.seat.SeatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CubeResponse(@JsonProperty("name") String name, @JsonProperty("seats") List<SeatResponse> seats) {

}
