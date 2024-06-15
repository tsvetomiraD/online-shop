package uni.project.online.shop.model.revolut;

public class PayDto {
    private String orderId;
    private SavedPayment savedPaymentMethod;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public SavedPayment getSavedPaymentMethod() {
        return savedPaymentMethod;
    }

    public void setSavedPaymentMethod(SavedPayment savedPaymentMethod) {
        this.savedPaymentMethod = savedPaymentMethod;
    }
}
