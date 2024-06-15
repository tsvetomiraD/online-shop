package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.revolut.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class RevolutService {
    @Autowired
    RequestService requestService;
    @Value("${revolut.token}")
    private String token;

    @Value("${revolut.order.path}")
    private String ordersPath;
    @Value("${revolut.customer.path}")
    private String customerPath;

    private Map<String, String> getHeaders(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + token);
        //headers.put("Content-Type", "application/json");
        headers.put("Revolut-Api-Version", "2023-09-01");

        return headers;
    }
    public void payForOrder(PaymentRequest body, String orderId) {
        String path = ordersPath + "/" + orderId + "/payments";
        requestService.request(body, path, "POST", null, getHeaders(token));
    }

    public OrderResponse createOrder(RevolutOrder body) {
        return requestService.request(body, ordersPath, "POST", OrderResponse.class, getHeaders(token));
    }

    public OrderResponse getOrderStatus(String orderId) {
        String path = ordersPath + "/" + orderId;
        return requestService.request(null, path, "GET", OrderResponse.class, getHeaders(token));
    }

    public CustomerPaymentMethods getCustomerPaymentMethods(String customerId) {
        String path = customerPath + "/" + customerId + "/payment-methods";
        return requestService.request(null, path, "GET", CustomerPaymentMethods.class, getHeaders(token));
    }

    public void deleteCustomerPaymentMethod(String methodId, String customerId) {
        String path = customerPath + "/" + customerId + "/payment-methods/" + methodId;
        requestService.request(null, path, "DELETE", null, getHeaders(token));
    }
}
