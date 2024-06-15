package uni.project.online.shop.model.revolut;

import java.util.List;

public class OrderResponse extends OrderDto{
    private String type;
    private String state;
    private CreateCustomerResponse customer;
    private List<Payments> payments;
    private String checkout_url;
    private String environment;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CreateCustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CreateCustomerResponse customer) {
        this.customer = customer;
    }

    public String getCheckout_url() {
        return checkout_url;
    }

    public void setCheckout_url(String checkout_url) {
        this.checkout_url = checkout_url;
    }

    public List<Payments> getPayments() {
        return payments;
    }

    public void setPayments(List<Payments> payments) {
        this.payments = payments;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
