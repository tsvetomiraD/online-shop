package uni.project.online.shop.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import uni.project.online.shop.model.*;
import uni.project.online.shop.model.order.*;
import uni.project.online.shop.model.paypal.*;
import uni.project.online.shop.model.revolut.*;
import uni.project.online.shop.repository.*;
import uni.project.online.shop.util.GetStatus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    RevolutService revolutService;
    @Autowired
    PayPalService payPalService;
    @Value("${delivery.price}")
    private double deliveryPrice;
    private static final String COMPLETED = "completed";


    //todo add webhooks for when guest and add payments
    //todo add unique order num?

    public OrderResponse order(Order order) {
        double aggregatedAmount = 0;
        for (ItemToAdd item : order.getItems()) {
            Product product = productRepository.getProduct(item.getProductId());
            if (product.getQuantity() < item.getQuantity())
                throw new RuntimeException("No enough quantity for item " + product.getName());
            aggregatedAmount += product.getPrice();
        }
        double price = order.getCode() != null ? checkCode(aggregatedAmount, order.getCode()).getTotal() : aggregatedAmount;

        OrderResponse orderResponse;
        User user = userRepository.getUserById(order.getUserId());

        if (order.getType().equals("ON DELIVERY")) {
            orderResponse = new OrderResponse();
            orderResponse.setState("completed");
            completeOrder(order);
        } else if (order.getType().equals("REVOLUT")) {
            RevolutOrder order1 = new RevolutOrder("EUR", price * 100, new CreateCustomer(order.getFirstName(), order.getEmail(), order.getPhone()));
            orderResponse = revolutService.createOrder(order1);

            if (user != null && user.getCustomerId() == null) {
                user.setCustomerId(orderResponse.getCustomer().getId());
                userRepository.editUserCustomerId(user);
            }

            Timer timer = new Timer();
            TimerTask task = new GetStatus(timer, 0, orderResponse.getId(), revolutService, orderRepository, this);
            timer.schedule(task, 30000);
        } else {
            PayPalOrder order1 = new PayPalOrder("CAPTURE", "EUR", String.valueOf(price));
            PayPalOrderResponse orderResp;
            orderResp = payPalService.createOrder(order1);
            orderResponse = new OrderResponse();
            orderResponse.setId(orderResp.getId());
            orderResponse.setState(orderResp.getStatus());
        }
        DbOrder billingOrder = new DbOrder(order, orderResponse, aggregatedAmount);
        orderRepository.order(billingOrder);
        for (ItemToAdd item : order.getItems()) {
            orderRepository.addOrderProduct(item.getProductId(), item.getQuantity(), billingOrder.getId());
        }
        return orderResponse;
    }

    private void completeOrder(Order order) {
        StringBuilder emailText = new StringBuilder("Поръчката ти е обработена. \nПробукти в поръчката: \n");
        for (ItemToAdd item : order.getItems()) {
            Long productId = item.getProductId();
            //orderRepository.deleteFromCart(productId, order.getUserId());
            orderRepository.editQuantity(productId, item.getQuantity(), "-");
            orderRepository.addOrderToProduct(productId, item.getQuantity());
            String productName = productRepository.getProduct(productId).getName();
            emailText.append(productName).append(" : ").append(item.getQuantity()).append("\n");
        }
        emailService.sendOrderCompleted(order.getEmail(), emailText.toString());
        orderRepository.deletePromoCode(order.getCode());
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public PromoCodeResponse checkCode(Double total, String code) {
        Integer percents = orderRepository.checkCode1(code);
        if (percents == null)
            throw new RuntimeException("Invalid code");
        return new PromoCodeResponse(total * percents / 100, percents, total - (total * percents / 100));
    }

    public CustomerPaymentMethods getClientPaymentMethods(User user) {
        if (user == null || user.getCustomerId() == null)
            return null;
        PaymentMethods[] resp = revolutService.getCustomerPaymentMethods(user.getCustomerId());
        return new CustomerPaymentMethods(resp);
    }

    public void pay(PayDto pay) {
        String orderId = pay.getOrderId();
        //PaymentRequest paymentRequest = new PaymentRequest(pay.getPaymentMethod());
        revolutService.payForOrder(new PaymentRequest(pay.getSavedPaymentMethod()), orderId);
        OrderResponse status;
        status = revolutService.getOrderStatus(orderId);
        if (status.getState().equals(COMPLETED)) {
            completePayment(orderId); //todo
            return;
        }
        Timer timer = new Timer();
        TimerTask task = new GetStatus(timer, 0, orderId, revolutService, orderRepository, this);
        timer.schedule(task, 30000);
    }

    public void capturePayPalOrder(StringValueModel orderId) {
        PayPalCaptureResponse response = payPalService.confirmOrder(orderId.getValue()); //todo
        orderRepository.updatePayPalCaptureId(orderId.getValue(), response.getPurchase_units().get(0).getPayments().getCaptures().get(0).getId());
        if (response.getStatus().equals("COMPLETED")) {
            completePayment(orderId.getValue());
        }
    }

    public PaymentStatus checkStatus(String orderId) {
        return new PaymentStatus(orderRepository.getPaymentStatusForOrder(orderId));
    }

    public void completePayment(String orderId) {
        String status = orderRepository.getPaymentStatusForOrder(orderId);
        if (status == null || status.equals(COMPLETED)) {
            return;
        }
        orderRepository.updateOrderStatus(orderId, COMPLETED);
        Order order = orderRepository.getOrderByOrderId(orderId);
        List<ItemToAdd> orderItems = orderRepository.getOrderItems(order.getId());
        order.setItems(orderItems);
        completeOrder(order);
    }

    public void deletePaymentMethod(StringValueModel methodId, User user) {
        revolutService.deleteCustomerPaymentMethod(methodId.getValue(), user.getCustomerId());
    }

    public void handlePaymentStatus(WebhookBodyRevolut body) {
        Order order = orderRepository.getOrderByOrderId(body.getOrder_id());
        if (order == null)
            return;

        switch (body.getEvent()) {
            case "ORDER_COMPLETED" -> completePayment(body.getOrder_id());
            case "ORDER_PAYMENT_DECLINED" -> orderRepository.updateOrderStatus(body.getOrder_id(), "declined");
            case "ORDER_PAYMENT_FAILED" -> orderRepository.updateOrderStatus(body.getOrder_id(), "failed");
            default -> orderRepository.updateOrderStatus(body.getOrder_id(), body.getEvent());
        }
    }

    public void handlePayPalStatusFromWebhook(WebhookBodyPayPal body) {
        String type = body.getEvent_type();

        if (type.equals("PAYMENT.CAPTURE.COMPLETED") || type.equals("CUSTOMER.PAYOUT.COMPLETED")
                || type.equals("CHECKOUT.ORDER.COMPLETED") || type.equals("PAYMENT.SALE.COMPLETED")) {
            completePayment(body.getId());
            return;
        }
        if (type.equals("CUSTOMER.PAYOUT.FAILED")) {
            orderRepository.updateOrderStatus(body.getId(), "failed");
            return;
        }
        if (type.equals("PAYMENT.CAPTURE.DENIED")) {
            orderRepository.updateOrderStatus(body.getId(), "declined");
            return;
        }
        orderRepository.updateOrderStatus(body.getId(), body.getEvent_type());
    }
}
