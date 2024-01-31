package ca.ulaval.glo4002.cafe.api.rest.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckInRequest(@JsonProperty("customer_id") String customerId,
                             @JsonProperty("customer_name") String customerName,
                             @JsonProperty("group_name") String groupName) {

}
