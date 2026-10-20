package com.learnspringboot.store.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Service
@Component
public class OrderServiceStarter {
    private PaymentServiceStarter paymentService;

    // nếu chỉ có 1 contructor thì không cần @Autowired
    public OrderServiceStarter() {
    }

    @Autowired
    public OrderServiceStarter(PaymentServiceStarter paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }

    public void setPaymentService(PaymentServiceStarter paymentService) {
        this.paymentService = paymentService;
    }
}
