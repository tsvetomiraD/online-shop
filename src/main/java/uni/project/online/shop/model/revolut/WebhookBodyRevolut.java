package uni.project.online.shop.model.revolut;

public class WebhookBodyRevolut {
    private String event;
    private String order_id;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
