package ca.ulaval.glo4002.cafe.domain.tax;

import ca.ulaval.glo4002.cafe.domain.menu.Price;

import java.util.Objects;

public class Bill {

    private final Price subTotal;
    private final Price taxAmount;
    private final Price tipAmount;
    private final Price total;

    public Bill(Price subTotal, Price taxPrice, Price tipAmount, Price total) {
        this.subTotal = subTotal;
        this.taxAmount = taxPrice;
        this.tipAmount = tipAmount;
        this.total = total;
    }

    public boolean valuesEqual(Bill bill) {
        return this.subTotal.compareTo(bill.getSubTotal()) == 0 &&
            this.taxAmount.compareTo(bill.getTaxAmount()) == 0 &&
            this.tipAmount.compareTo(bill.getTipAmount()) == 0 &&
            this.total.compareTo(bill.getTotal()) == 0;
    }

    public Price getSubTotal() {
        return subTotal;
    }

    public Price getTaxAmount() {
        return taxAmount;
    }

    public Price getTotal() {
        return total;
    }

    public Price getTipAmount() {
        return tipAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Bill bill = (Bill) o;
        return Objects.equals(subTotal, bill.subTotal) && Objects.equals(taxAmount, bill.taxAmount)
            && Objects.equals(total, bill.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTotal, taxAmount, total);
    }
}
