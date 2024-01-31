package ca.ulaval.glo4002.cafe.api.rest.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckoutRequest(@JsonProperty("customer_id") String customerId) {

}
