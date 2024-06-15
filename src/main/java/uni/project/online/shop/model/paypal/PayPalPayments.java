package uni.project.online.shop.model.paypal;


import java.util.List;

public class PayPalPayments {
    private List<Capture> captures;

    public List<Capture> getCaptures() {
        return captures;
    }

    public void setCaptures(List<Capture> captures) {
        this.captures = captures;
    }
}
