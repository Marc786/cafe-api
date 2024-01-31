package ca.ulaval.glo4002.cafe.domain.seat;

public enum Status {
    AVAILABLE("Available"),
    OCCUPIED("Occupied"),
    RESERVED("Reserved");

    public final String status;

    Status(String status) {
        this.status = status;
    }
}
