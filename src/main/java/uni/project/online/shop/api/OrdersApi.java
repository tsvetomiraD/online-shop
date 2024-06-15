package uni.project.online.shop.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import uni.project.online.shop.model.order.Order;
import uni.project.online.shop.model.order.PaymentStatus;
import uni.project.online.shop.model.order.PromoCodeResponse;
import uni.project.online.shop.model.StringValueModel;
import uni.project.online.shop.model.revolut.CustomerPaymentMethods;
import uni.project.online.shop.model.revolut.OrderResponse;
import uni.project.online.shop.model.revolut.PayDto;

@RequestMapping("/order")
public interface OrdersApi {
    @Operation(tags = {"Order"}, summary = "login")
    @PostMapping()
    public OrderResponse order(@RequestBody Order order);

    @Operation(tags = {"Order"}, summary = "login")
    @GetMapping("/delivery-price")
    public double deliveryPrice();

    @Operation(tags = {"Order"}, summary = "login")
    @PostMapping("/pay")
    public void pay(@RequestBody PayDto pay);

    @Operation(tags = {"Order"}, summary = "login")
    @PostMapping("/capture-paypal")
    public void capturePayPal(@RequestBody StringValueModel orderId);

    @Operation(tags = {"Order"}, summary = "login")
    @GetMapping("/get-payment-methods")
    public CustomerPaymentMethods getPaymentMethods(HttpSession session);

    @Operation(tags = {"Order"}, summary = "login")
    @DeleteMapping("/delete-payment-method")
    public void deletePaymentMethod(@RequestBody StringValueModel methodId, HttpSession session);

    @Operation(tags = {"Order"}, summary = "login")
    @GetMapping("/get-payment-status")
    public PaymentStatus getPaymentStatus(@RequestParam("orderId") String orderId);

    @Operation(tags = {"Order"}, summary = "login")
    @GetMapping("/check-code")
    public PromoCodeResponse checkCode(@RequestParam Double total, @RequestParam String code);
}
