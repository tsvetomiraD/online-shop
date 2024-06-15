package uni.project.online.shop.model.order;

import uni.project.online.shop.model.revolut.OrderResponse;

public class DbOrder {
    private Long id;
    private Long userId;
    private String orderId;
    private String publicId;
    private String status;
    private double totalPrice;
    private String email;
    private String address;
    private String phone;
    private String firstName;
    private String lastName;
    private String type;

    public DbOrder() {}
    public DbOrder(Order order, OrderResponse orderResponse, double price) {
        this.userId = order.getUserId();
        this.orderId = orderResponse.getId();
        this.publicId = orderResponse.getToken();
        this.status = orderResponse.getState();
        this.totalPrice = price;
        this.email = order.getEmail(); //todo maybe get from user?
        this.address = order.getAddress();
        this.phone = order.getPhone();
        this.firstName = order.getFirstName();
        this.lastName = order.getLastName();
        this.type = order.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
