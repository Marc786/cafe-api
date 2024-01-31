package ca.ulaval.glo4002.cafe.api.rest.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record BillResponse(@JsonProperty("orders") List<String> orders,
                           @JsonProperty("subtotal") BigDecimal subtotal,
                           @JsonProperty("taxes") BigDecimal taxes,
                           @JsonProperty("tip") BigDecimal tip,
                           @JsonProperty("total") BigDecimal total) {

}
