package ca.ulaval.glo4002.cafe.domain.menu;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.tax.Rate;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class PriceTest {

    @Test
    void addsPrice_returnSum() {
        Price price = new Price(0.1);
        Price priceToAdd = new Price(0.13);
        Price expectedPrice = new Price(0.23);

        Price newRate = price.add(priceToAdd);

        assertThat(newRate).isEqualTo(expectedPrice);
    }

    @Test
    void multiplyPrice_returnProduct() {
        Price price = new Price(0.1);
        Rate rate = new Rate(0.13);
        Price expectedPrice = new Price(0.013);

        Price newRate = price.multiply(rate);

        assertThat(newRate.compareTo(expectedPrice)).isEqualTo(0);
    }

    @Test
    void roundUp_returnExpectedPrice() {
        Price price = new Price(12.12000001);
        Price expectedPrice = new Price(12.13);

        BigDecimal roundedPrice = price.roundUp();

        assertThat(roundedPrice).isEqualTo(expectedPrice.getValue());
    }
}
