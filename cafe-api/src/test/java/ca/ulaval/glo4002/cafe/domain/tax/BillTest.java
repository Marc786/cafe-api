package ca.ulaval.glo4002.cafe.domain.tax;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.menu.Price;

import org.junit.jupiter.api.Test;

class BillTest {

    @Test
    void sameBill_valuesEqual_billValuesAreEquals() {
        Bill bill = new Bill(new Price(0), new Price(0), new Price(0), new Price(0));
        Bill billToCompare = new Bill(new Price(0), new Price(0), new Price(0), new Price(0));

        boolean isEqual = bill.valuesEqual(billToCompare);

        assertThat(isEqual).isTrue();
    }

    @Test
    void differentBill_valuesEqual_billValuesAreNotEquals() {
        Bill bill = new Bill(new Price(0), new Price(0), new Price(0), new Price(0));
        Bill billToCompare = new Bill(new Price(1), new Price(0), new Price(0), new Price(0));

        boolean isEqual = bill.valuesEqual(billToCompare);

        assertThat(isEqual).isFalse();
    }
}
