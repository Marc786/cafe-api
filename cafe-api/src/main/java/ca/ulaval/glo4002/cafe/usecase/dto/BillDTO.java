package ca.ulaval.glo4002.cafe.usecase.dto;

import ca.ulaval.glo4002.cafe.domain.menu.Price;

public record BillDTO(Price subtotal, Price tax, Price tip, Price total, OrderDTO order) {

}
