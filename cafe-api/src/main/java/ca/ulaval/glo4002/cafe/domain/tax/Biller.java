package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.menu.MenuItem;
import ca.ulaval.glo4002.cafe.domain.menu.Price;

import java.util.List;
import java.util.Objects;

public class Biller {

    private final Rate countryRate;
    private final Rate stateRate;
    private final Rate tipRate;

    public Biller(Rate countryRate, Rate stateRate, Rate tipRate) {
        this.countryRate = countryRate;
        this.stateRate = stateRate;
        this.tipRate = tipRate;
    }

    public Rate getCountryRate() {
        return countryRate;
    }

    public Rate getStateRate() {
        return stateRate;
    }

    public Rate getTipRate() {
        return tipRate;
    }

    public Bill createBill(List<MenuItem> items, boolean isInGroup) {
        Price subTotal = calculateSubTotal(items);
        Rate totalRate = calculateTotalTaxRate();
        Price taxPrice = subTotal.multiply(totalRate);
        Price tipPrice = calculateTipAmount(subTotal, isInGroup);
        Price total = subTotal.add(taxPrice).add(tipPrice);

        return new Bill(subTotal, taxPrice, tipPrice, total);
    }

    private Price calculateTipAmount(Price subTotal, boolean isInGroup) {
        if (isInGroup)
            return subTotal.multiply(tipRate);
        else
            return new Price(0);
    }

    private Price calculateSubTotal(List<MenuItem> items) {
        return items.stream().map(MenuItem::getPrice).reduce(new Price(0), Price::add);
    }

    private Rate calculateTotalTaxRate() {
        return countryRate.add(stateRate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Biller that = (Biller) o;
        return Objects.equals(getCountryRate(), that.getCountryRate()) && Objects.equals(getStateRate(),
            that.getStateRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryRate(), getStateRate());
    }
}
