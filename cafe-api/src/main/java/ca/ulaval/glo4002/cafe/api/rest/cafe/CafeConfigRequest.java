package ca.ulaval.glo4002.cafe.api.rest.cafe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CafeConfigRequest(@JsonProperty("group_reservation_method") String groupReservationMethod,
                                @JsonProperty("organization_name") String organizationName,
                                @JsonProperty("cube_size") int cubeSize,
                                @JsonProperty("country") String country,
                                @JsonProperty("province") String province,
                                @JsonProperty("state") String state,
                                @JsonProperty("group_tip_rate") double tip) {

}
