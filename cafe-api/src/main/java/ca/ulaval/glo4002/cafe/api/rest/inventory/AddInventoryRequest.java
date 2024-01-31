package ca.ulaval.glo4002.cafe.api.rest.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddInventoryRequest(@JsonProperty(value = "Chocolate", required = true) int chocolateQuantity,
                                  @JsonProperty(value = "Espresso", required = true) int espressoQuantity,
                                  @JsonProperty(value = "Milk", required = true) int milkQuantity,
                                  @JsonProperty(value = "Water", required = true) int waterQuantity) {

}
