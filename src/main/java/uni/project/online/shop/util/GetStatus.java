package uni.project.online.shop.util;


import uni.project.online.shop.model.revolut.OrderResponse;
import uni.project.online.shop.repository.OrderRepository;
import uni.project.online.shop.service.OrderService;
import uni.project.online.shop.service.RevolutService;

import java.util.Timer;
import java.util.TimerTask;

public class GetStatus extends TimerTask {
    int count = 0;
    int[] intervals = {1, 2, 3, 5}; // Time intervals in minutes
    Timer timer;
    String orderId;
    RevolutService revolutService;
    OrderRepository orderRepository;
    OrderService orderService;

    public GetStatus(Timer timer, int count, String orderId, RevolutService revolutService, OrderRepository orderRepository, OrderService orderService) {
        this.timer = timer;
        this.count = count;
        this.orderId = orderId;
        this.orderRepository = orderRepository;
        this.revolutService = revolutService;
        this.orderService = orderService;
    }
    @Override
    public void run() {
        String status = orderRepository.getPaymentStatusForOrder(orderId);
        if (status.equals("completed")) {
            timer.cancel();
            return;
        }
        if (count < intervals.length) {
            OrderResponse statusFromRequest = revolutService.getOrderStatus(orderId);
            if (statusFromRequest.getState().equals("completed")) {
                orderService.completePayment(orderId);
                timer.cancel();
                return;
            }
            orderRepository.updateOrderStatus(orderId, statusFromRequest.getState());
            long delay = (long) intervals[count] * 60 * 1000;
            this.cancel();
            TimerTask task = new GetStatus(timer, ++count, orderId, revolutService,orderRepository, orderService);
            timer.schedule(task, delay);
        } else {
            timer.cancel();
        }
    }
}
