package ca.ulaval.glo4002.cafe.api.rest.reservation;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import ca.ulaval.glo4002.cafe.domain.reservation.exception.InvalidGroupSizeException;

import org.junit.jupiter.api.Test;

class ReservationParamsTest {

    private final static String A_GROUP_NAME = "group name";
    private final static int A_VALID_SIZE = 2;
    private final static int AN_INVALID_SIZE = 1;

    @Test
    void validGroupSize_createReservationParams_createdWithExpectedAttributes() {
        ReservationParams reservationParams = new ReservationParams(A_GROUP_NAME, A_VALID_SIZE);

        assertThat(reservationParams.getGroupName()).isEqualTo(A_GROUP_NAME);
        assertThat(reservationParams.getGroupSize()).isEqualTo(A_VALID_SIZE);
    }

    @Test
    void invalidGroupSize_createReservationParams_shouldThrowsInvalidGroupSizeException() {
        assertThrows(InvalidGroupSizeException.class, () -> new ReservationParams(A_GROUP_NAME, AN_INVALID_SIZE));
    }
}
