package ca.ulaval.glo4002.cafe.domain.reservation;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

class ReservationFactoryTest {

    private static final String GROUP_NAME = "AGROUPNAME";
    private static final int VALID_GROUP_SIZE = 2;

    @Test
    void createReservation_returnsExpectedReservation() {
        ReservationFactory reservationFactory = new ReservationFactory();
        Reservation expectedReservation = new Reservation(GROUP_NAME, VALID_GROUP_SIZE);

        Reservation reservation = reservationFactory.create(GROUP_NAME, VALID_GROUP_SIZE);

        assertThat(reservation).isEqualTo(expectedReservation);
    }
}
