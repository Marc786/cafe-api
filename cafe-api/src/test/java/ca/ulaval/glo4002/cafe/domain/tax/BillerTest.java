package ca.ulaval.glo4002.cafe.domain.tax;

import static com.google.common.truth.Truth.assertThat;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BillerTest {

    private static final String ITEM_NAME_1 = "antho";
    private static final String ITEM_NAME_2 = "jackcrack";
    private static final Rate COUNTRY_RATE = new Rate(0.05);
    private static final Rate STATE_RATE = new Rate(0.1);
    private static final Rate TIP_RATE = new Rate(0.05);
    private static final boolean IN_GROUP = true;
    private static final boolean NO_GROUP = false;

    private Biller biller;

    @BeforeEach
    void createBiller() {
        biller = new Biller(COUNTRY_RATE, STATE_RATE, TIP_RATE);
    }

    @Test
    void emptyOrder_createBill_returnEmptyBill() {
        Bill expectedBill = new Bill(new Price(0), new Price(0), new Price(0), new Price(0));

        Bill bill = biller.createBill(new ArrayList<>(), NO_GROUP);

        assertThat(bill.valuesEqual(expectedBill)).isTrue();
    }

    @Test
    void orderWithOneElement_createBill_returnExpectedBill() {
        MenuItem item = new MenuItem(ITEM_NAME_1, new Price(1000), new ArrayList<>());
        Bill expectedBill = new Bill(new Price(1000), new Price(150), new Price(0), new Price(1150));

        Bill bill = biller.createBill(List.of(item), NO_GROUP);

        assertThat(bill.valuesEqual(expectedBill)).isTrue();
    }

    @Test
    void orderWithGroup_createBill_returnExpectedBillWithGroupTip() {
        MenuItem item = new MenuItem(ITEM_NAME_1, new Price(1000), new ArrayList<>());
        Bill expectedBill = new Bill(new Price(1000), new Price(150), new Price(50), new Price(1200));

        Bill bill = biller.createBill(List.of(item), IN_GROUP);

        assertThat(bill.valuesEqual(expectedBill)).isTrue();
    }

    @Test
    void orderWithMultipleElement_createBill_returnExpectedBill() {
        MenuItem item = new MenuItem(ITEM_NAME_1, new Price(1000.98), new ArrayList<>());
        MenuItem item2 = new MenuItem(ITEM_NAME_2, new Price(3000.36), new ArrayList<>());
        Bill expectedBill = new Bill(new Price(4001.34), new Price(600.201), new Price(0), new Price(4601.541));

        Bill bill = biller.createBill(List.of(item, item2), NO_GROUP);

        assertThat(bill.valuesEqual(expectedBill)).isTrue();
    }
}
