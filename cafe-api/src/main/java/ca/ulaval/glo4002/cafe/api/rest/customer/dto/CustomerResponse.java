package ca.ulaval.glo4002.cafe.api.rest.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerResponse(@JsonProperty("name") String name, @JsonProperty("seat_number") int seatNumber,
                               @JsonProperty("group_name") String groupName) {

}
