package ca.ulaval.glo4002.cafe.infra.dto;


import java.util.Objects;

public class InMemoryBill {

    private final double subTotal;
    private final double taxAmount;
    private final double tipAmount;
    private final double total;

    public InMemoryBill(double subTotal, double taxAmount, double tipAmount, double total) {
        this.subTotal = subTotal;
        this.taxAmount = taxAmount;
        this.tipAmount = tipAmount;
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotal() {
        return total;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InMemoryBill that = (InMemoryBill) o;
        return Double.compare(that.getSubTotal(), getSubTotal()) == 0
            && Double.compare(that.getTaxAmount(), getTaxAmount()) == 0
            && Double.compare(that.getTotal(), getTotal()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubTotal(), getTaxAmount(), getTotal());
    }
}
