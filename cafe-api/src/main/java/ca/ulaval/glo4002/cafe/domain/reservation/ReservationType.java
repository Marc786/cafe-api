package ca.ulaval.glo4002.cafe.domain.reservation;

public enum ReservationType {
    DEFAULT("Default"),
    FULL_CUBES("Full Cubes"),
    NO_LONERS("No Loners");

    public final String type;

    ReservationType(String type) {
        this.type = type;
    }
}
