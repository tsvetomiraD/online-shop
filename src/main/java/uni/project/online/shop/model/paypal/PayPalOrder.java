package uni.project.online.shop.model.paypal;

import java.util.ArrayList;

public class PayPalOrder {
    private String intent;
    private ArrayList<PurchaseUnits> purchase_units;

    public PayPalOrder() {}
    public PayPalOrder(String intent, String currency, String value) {
        this.intent = intent;
        this.purchase_units = new ArrayList<>();
        PurchaseUnits purchaseUnits = new PurchaseUnits();
        Amount amount = new Amount();
        amount.setValue(value);
        amount.setCurrency_code(currency);
        purchaseUnits.setAmount(amount);
        purchase_units.add(purchaseUnits);
    }

    public PayPalOrder(String intent, String currency, String value, String description) {
        this.intent = intent;
        this.purchase_units = new ArrayList();
        PurchaseUnits purchaseUnits = new PurchaseUnits();
        Amount amount = new Amount();
        amount.setValue(value);
        amount.setCurrency_code(currency);
        purchaseUnits.setAmount(amount);
        purchaseUnits.setDescription(description);
        this.purchase_units.add(purchaseUnits);
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public ArrayList<PurchaseUnits> getPurchase_units() {
        return purchase_units;
    }

    public void setPurchase_units(ArrayList<PurchaseUnits> purchase_units) {
        this.purchase_units = purchase_units;
    }
}
