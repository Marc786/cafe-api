package ca.ulaval.glo4002.cafe.api.rest.cafe;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InvalidGroupReservationMethodException;
import ca.ulaval.glo4002.cafe.domain.reservation.ReservationType;
import ca.ulaval.glo4002.cafe.domain.tax.exception.InvalidGroupTipException;

import java.util.Arrays;

public class CafeParams {

    private final ReservationType reservationType;
    private final String cafeName;
    private final int cubeSize;

    private final double tip;

    public CafeParams(String organizationName, int cubeSize, String groupReservationMethod, double tip) {
        this.reservationType = validateReservationMethod(groupReservationMethod);
        this.cafeName = organizationName;
        this.cubeSize = cubeSize;
        this.tip = validateGroupTip(tip);
    }

    private ReservationType validateReservationMethod(String reservationMethod) {
        return Arrays.stream(ReservationType.values())
            .filter(reservationType -> reservationType.type.equals(reservationMethod))
            .findFirst()
            .orElseThrow(InvalidGroupReservationMethodException::new);
    }

    private double validateGroupTip(double tip) {
        if (tip >= 0 && tip <= 100)
            return tip / 100;
        else
            throw new InvalidGroupTipException();
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public String getCafeName() {
        return cafeName;
    }

    public int getCubeSize() {
        return cubeSize;
    }

    public double getTip() {
        return tip;
    }
}
