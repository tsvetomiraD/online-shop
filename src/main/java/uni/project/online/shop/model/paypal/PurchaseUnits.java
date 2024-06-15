package uni.project.online.shop.model.paypal;

public class PurchaseUnits {
    private Amount amount;
    private String description;

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
