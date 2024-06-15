package uni.project.online.shop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import uni.project.online.shop.api.OrdersApi;
import uni.project.online.shop.model.*;
import uni.project.online.shop.model.order.Order;
import uni.project.online.shop.model.order.PaymentStatus;
import uni.project.online.shop.model.order.PromoCodeResponse;
import uni.project.online.shop.model.revolut.CustomerPaymentMethods;
import uni.project.online.shop.model.revolut.OrderResponse;
import uni.project.online.shop.model.revolut.PayDto;
import uni.project.online.shop.service.AuthService;
import uni.project.online.shop.service.OrderService;

@RestController
public class OrderController implements OrdersApi {
    @Autowired
    OrderService orderService;
    @Autowired
    AuthService authService;

    @Override
    public OrderResponse order(Order order) {
       return orderService.order(order);
    }

    @Override
    public double deliveryPrice() {
        return orderService.getDeliveryPrice();
    }

    @Override
    public void pay(PayDto pay) {
        orderService.pay(pay);
    }

    @Override
    public void capturePayPal(StringValueModel orderId) {
        orderService.capturePayPalOrder(orderId);
    }

    @Override
    public CustomerPaymentMethods getPaymentMethods(HttpSession session) {
        User user = authService.getUserFromHttpSession(session);
        return orderService.getClientPaymentMethods(user);
    }

    @Override
    public void deletePaymentMethod(StringValueModel methodId, HttpSession session) {
        User user = authService.getUserFromHttpSession(session);
        orderService.deletePaymentMethod(methodId, user);
    }

    @Override
    public PaymentStatus getPaymentStatus(String orderId) {
        return orderService.checkStatus(orderId);
    }

    @Override
    public PromoCodeResponse checkCode(Double total, String code) {
        return orderService.checkCode(total, code);
    }
}
