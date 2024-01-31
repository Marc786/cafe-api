package ca.ulaval.glo4002.cafe.api.rest.cafe;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CafeResponse(@JsonProperty("name") String name, @JsonProperty("cubes") List<CubeResponse> cubes) {

}
