package ca.ulaval.glo4002.cafe.domain.tax.state;

public enum USState implements State<USState> {
    AB("AL", 0.04),
    BC("AZ", 0.056),
    MB("CA", 0.0725),
    NB("FL", 0.06),
    NL("ME", 0.055),
    NT("NY", 0.04),
    NS("TX", 0.0625);


    private final String state;
    private final double taxRate;

    USState(String state, double taxRate) {
        this.state = state;
        this.taxRate = taxRate;
    }

    @Override
    public String getName() {
        return state;
    }

    @Override
    public double getRate() {
        return taxRate;
    }
}
