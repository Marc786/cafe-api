package ca.ulaval.glo4002.cafe.domain.tax.state;

public enum CanadaProvince implements State<CanadaProvince> {
    AB("AB", 0),
    BC("BC", 0.07),
    MB("MB", 0.07),
    NB("NB", 0.1),
    NL("NL", 0.1),
    NT("NT", 0),
    NS("NS", 0.1),
    NU("NU", 0),
    ON("ON", 0.08),
    PE("PE", 0.1),
    QC("QC", 0.09975),
    SK("SK", 0.06),
    YT("YT", 0);

    private final String province;
    private final double taxRate;

    CanadaProvince(String province, double taxRate) {
        this.province = province;
        this.taxRate = taxRate;
    }

    @Override
    public String getName() {
        return province;
    }

    @Override
    public double getRate() {
        return taxRate;
    }
}
