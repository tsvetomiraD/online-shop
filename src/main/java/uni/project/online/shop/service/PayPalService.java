package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.*;
import uni.project.online.shop.model.paypal.*;

import java.util.*;

@Service
public class PayPalService {
    @Autowired
    private RequestService requestService;

    @Value("${paypal.id}")
    private String clientId;
    @Value("${paypal.path}")
    private String url;
    @Value("${paypal.secret}")
    private String clientSecret;

    private Map<String, String> getHeaders() {
        Map<String, String> headers= new HashMap<>();
        String token = getToken().getAccessToken();
        headers.put("Authorization", "Bearer " + token);
        String uuid = String.valueOf(UUID.randomUUID());
        headers.put("PayPal-Request-Id", uuid);

        return headers;
    }

    public Token getToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString(String.format("%s:%s", clientId, clientSecret).getBytes()));
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> formData = new ArrayList<>();
        formData.add(new NameValuePair("grant_type", "client_credentials"));

        return requestService.getTokenRequest(url + "v1/oauth2/token", headers, formData);
    }

    public PayPalCaptureResponse confirmOrder(String orderId) {
        String path = url + "v2/checkout/orders/" + orderId + "/capture";
        return requestService.request(null, path, "POST", PayPalCaptureResponse.class, getHeaders());
    }
    public PayPalOrderResponse createOrder(PayPalOrder body) {
        String path = url +"v2/checkout/orders";
        return requestService.request(body, path, "POST", PayPalOrderResponse.class, getHeaders());
    }
}