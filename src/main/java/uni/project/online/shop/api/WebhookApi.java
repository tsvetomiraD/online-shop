package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uni.project.online.shop.model.paypal.WebhookBodyPayPal;
import uni.project.online.shop.model.revolut.WebhookBodyRevolut;

@RequestMapping("/webhook")
public interface WebhookApi {
    @Operation(tags = {"Webhook"}, summary = "login")
    @PostMapping("/revolut/payment-status")
    public void getPaymentStatus(@RequestBody WebhookBodyRevolut body);

    @Operation(tags = {"Webhook"}, summary = "login")
    @PostMapping("/payPal/payment-status")
    public void getPaymentStatusForPayPal(@RequestBody WebhookBodyPayPal body);
}
