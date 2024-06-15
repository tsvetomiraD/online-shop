package uni.project.online.shop.model.order;

public class PaymentStatus {
    private String status;

    public PaymentStatus() {
    }

    public PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
