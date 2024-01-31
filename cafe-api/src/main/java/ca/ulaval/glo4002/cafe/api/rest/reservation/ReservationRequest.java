package ca.ulaval.glo4002.cafe.api.rest.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReservationRequest(@JsonProperty("group_name") String groupName,
                                 @JsonProperty("group_size") int groupSize) {

}
