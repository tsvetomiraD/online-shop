package uni.project.online.shop.model.revolut;

public class RevolutOrder {
    private String currency;
    private double amount;
    private CreateCustomer customer;

    public RevolutOrder() {}
    public RevolutOrder(String currency, double amount, CreateCustomer customer) {
        this.currency = currency;
        this.amount = amount;
        this.customer = customer;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CreateCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(CreateCustomer customer) {
        this.customer = customer;
    }
}
