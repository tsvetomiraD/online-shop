package uni.project.online.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.WebhookApi;
import uni.project.online.shop.model.paypal.WebhookBodyPayPal;
import uni.project.online.shop.model.revolut.WebhookBodyRevolut;
import uni.project.online.shop.service.OrderService;

@RestController
public class WebhookController implements WebhookApi {
    @Autowired
    OrderService orderService;
    @Override
    public void getPaymentStatus(WebhookBodyRevolut body) {
        orderService.handlePaymentStatus(body);
    }

    @Override
    public void getPaymentStatusForPayPal(WebhookBodyPayPal body) {
        orderService.handlePayPalStatusFromWebhook(body);
    }
}
