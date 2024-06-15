package uni.project.online.shop.model.revolut;

import java.util.List;

public class CustomerPaymentMethods {
    private List<PaymentMethods> paymentMethods;

    public List<PaymentMethods> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethods> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
