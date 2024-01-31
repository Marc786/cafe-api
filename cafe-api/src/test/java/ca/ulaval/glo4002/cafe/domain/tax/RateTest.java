package ca.ulaval.glo4002.cafe.domain.tax;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class RateTest {

    @Test
    void addsRate_returnSum() {
        Rate rate = new Rate(0.1);
        Rate rateToAdd = new Rate(0.13);
        Rate expectedRate = new Rate(0.23);

        Rate newRate = rate.add(rateToAdd);

        assertThat(newRate).isEqualTo(expectedRate);
    }
}
