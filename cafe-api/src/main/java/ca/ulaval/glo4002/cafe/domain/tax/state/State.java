package ca.ulaval.glo4002.cafe.domain.tax.state;

public interface State<T extends State<T>> {

    String getName();

    double getRate();
}
