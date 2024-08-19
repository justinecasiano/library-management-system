package models;

public class Penalty {
    private double amount;
    private double paid;
    private double change;

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getAmount() {
        return amount;
    }

    public double getPaid() {
        return paid;
    }

    public double getChange() {
        return change;
    }
}
