package ca.ulaval.glo4002.cafe.api.rest.cafe;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.tax.exception.InvalidGroupTipException;

import org.junit.jupiter.api.Test;

class CafeParamsTest {

    private final static String AN_ORGANIZATION_NAME = "mon cafe";
    private final static int A_CUBE_SIZE = 4;
    private final static String A_VALID_RESERVATION_METHOD = "Full Cubes";
    private final static String A_INVALID_RESERVATION_METHOD = "invalid";
    private final static double TIP = 1;
    private final static double INVALID_TIP_BIGGER = 100.01;
    private final static double INVALID_TIP_SMALLER = -0.01;


    @Test
    void validReservationMethod_createCafeParams_createdWithExpectedAttributes() {
        double expectedTipRate = 0.01;

        CafeParams cafeParams = new CafeParams(AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_VALID_RESERVATION_METHOD, TIP);

        assertThat(cafeParams.getCafeName()).isEqualTo(AN_ORGANIZATION_NAME);
        assertThat(cafeParams.getCubeSize()).isEqualTo(A_CUBE_SIZE);
        assertThat(cafeParams.getReservationType()).isEqualTo(ReservationType.FULL_CUBES);
        assertThat(cafeParams.getTip()).isEqualTo(expectedTipRate);
    }

    @Test
    void invalidReservationMethod_createCafeParams_shouldThrowsInvalidGroupReservationMethodException() {
        assertThrows(InvalidGroupReservationMethodException.class, () ->
            new CafeParams(AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_INVALID_RESERVATION_METHOD, TIP));
    }

    @Test
    void groupTipRateBigger_validateGroupTip_shouldThrowsInvalidGroupTipException() {
        assertThrows(InvalidGroupTipException.class, () ->
            new CafeParams(AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_VALID_RESERVATION_METHOD, INVALID_TIP_BIGGER));
    }


    @Test
    void groupTipRateSmaller_validateGroupTip_shouldThrowsInvalidGroupTipException() {
        assertThrows(InvalidGroupTipException.class, () ->
            new CafeParams(AN_ORGANIZATION_NAME, A_CUBE_SIZE, A_VALID_RESERVATION_METHOD, INVALID_TIP_SMALLER));
    }
}
