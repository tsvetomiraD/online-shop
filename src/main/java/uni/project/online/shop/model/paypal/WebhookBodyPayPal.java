package uni.project.online.shop.model.paypal;

public class WebhookBodyPayPal {
    private String id;
    private String event_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }
}
