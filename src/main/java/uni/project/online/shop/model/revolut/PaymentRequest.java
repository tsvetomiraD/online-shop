package uni.project.online.shop.model.revolut;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("saved_payment_method")
    private SavedPayment savedPaymentMethod;

    public PaymentRequest() {}
    public PaymentRequest(PaymentMethods paymentMethod) {
        this.savedPaymentMethod = new SavedPayment();
        savedPaymentMethod.setType(paymentMethod.getType().toLowerCase());
        savedPaymentMethod.setId(paymentMethod.getId());
        savedPaymentMethod.setInitiator(paymentMethod.getSavedFor().toLowerCase());
    }

    public PaymentRequest(SavedPayment paymentMethod) {
        this.savedPaymentMethod = paymentMethod;
    }


    public SavedPayment getSavedPaymentMethod() {
        return savedPaymentMethod;
    }

    public void setSavedPaymentMethod(SavedPayment savedPaymentMethod) {
        this.savedPaymentMethod = savedPaymentMethod;
    }
}
