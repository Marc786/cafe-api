package ca.ulaval.glo4002.cafe.api.rest.seat;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SeatResponse(@JsonProperty("number") int number, @JsonProperty("status") String status,
                           @JsonProperty("customer_id") String customerId,
                           @JsonProperty("group_name") String groupName) {

}
