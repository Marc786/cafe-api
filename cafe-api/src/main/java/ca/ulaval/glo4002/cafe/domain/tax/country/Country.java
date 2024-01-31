package ca.ulaval.glo4002.cafe.domain.tax.country;

public enum Country {
    CA("CA", 0.05),
    US("US", 0),
    CL("CL", 0.19),
    NONE("None", 0);

    public final String name;
    public final double taxRate;

    Country(String name, double taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }
}
