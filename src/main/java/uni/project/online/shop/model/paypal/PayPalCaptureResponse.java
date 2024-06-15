package uni.project.online.shop.model.paypal;

import java.util.List;

public class PayPalCaptureResponse {
    private String id;
    private String status;
    private List<PurchaseUnitsCapture> purchase_units;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseUnitsCapture> getPurchase_units() {
        return purchase_units;
    }

    public void setPurchase_units(List<PurchaseUnitsCapture> purchase_units) {
        this.purchase_units = purchase_units;
    }
}
