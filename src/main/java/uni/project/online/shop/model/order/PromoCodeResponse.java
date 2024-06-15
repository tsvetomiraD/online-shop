package uni.project.online.shop.model.order;

public class PromoCodeResponse {
    private double discount;
    private int percents;
    private double total;

    public PromoCodeResponse() {}
    public PromoCodeResponse(double discount, int percents, double total) {
        this.discount = discount;
        this.percents = percents;
        this.total = total;
    }
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getPercents() {
        return percents;
    }

    public void setPercents(int percents) {
        this.percents = percents;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
